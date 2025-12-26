package gregtech.common.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.ForgeDirection;

import org.lwjgl.opengl.GL11;

import com.gtnewhorizon.structurelib.alignment.enumerable.Rotation;
import gregtech.api.enums.Dyes;
import gregtech.common.tileentities.machines.multi.realitysiphon.MTERealityFabricSiphon;

public class RealityFabricSiphonRenderer {

    public static final ResourceLocation SIPHON_TEXTURE = new ResourceLocation("textures/entity/end_portal.png");

    public static void renderTileEntityAt(MTERealityFabricSiphon mteRealityFabricSiphon, double x, double y, double z, float timeSinceLastTick) {
        final var baseMetaTileEntity = mteRealityFabricSiphon.getBaseMetaTileEntity();
        if( baseMetaTileEntity != null && baseMetaTileEntity.isActive() ) {
            GL11.glPushMatrix();

            GL11.glTranslated(x, y, z);
            renderBoundingBoxWireframe(mteRealityFabricSiphon.getRenderBoundingBox((int)x, (int)y, (int)z));

            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glTranslated(0.5, 0, 0.5);

            var mc = Minecraft.getMinecraft();
            if(mteRealityFabricSiphon.renderCore()) renderCore(mteRealityFabricSiphon, mc.theWorld.getTotalWorldTime()+timeSinceLastTick);
            if(mteRealityFabricSiphon.renderSiphon()) renderSiphon(mteRealityFabricSiphon, mc.theWorld.getTotalWorldTime()+timeSinceLastTick);

            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glPopMatrix();
        }
    }

    private static void renderCore(MTERealityFabricSiphon mteRealityFabricSiphon, float time) {
        GL11.glDisable(GL11.GL_TEXTURE_2D);

        GL11.glPushMatrix();
        RealityFabricSiphonRenderer.rotate(mteRealityFabricSiphon.getDirection(), mteRealityFabricSiphon.getRotation());
        GL11.glTranslated(0, 0, 2);

        Tessellator tessellator = Tessellator.instance;

        float pulseTime = (time * 0.05f) % 1.0f; // 1 second cycle
        float animatedWidth = 0.75f + 0.25f * (float) Math.sin(pulseTime * 2 * Math.PI); // 0.5 to 1.0 (1 to 2 blocks)

        float r,g,b;
        if( mteRealityFabricSiphon.getColor() < 0){
            // Light rainbow color that cycles over time globally
            float colorTime = (time * 0.1f) % 1.0f;
            float hue = colorTime * 2 * (float) Math.PI;

            r = 0.7f + 0.3f * (float) Math.sin(hue);
            g = 0.7f + 0.3f * (float) Math.sin(hue + 2.094f); // 120 degrees
            b = 0.7f + 0.3f * (float) Math.sin(hue + 4.188f); // 240 degrees
        }else{
            int rgb = Dyes.get(mteRealityFabricSiphon.getColor()).rgba;
            r = ((rgb >> 24) & 0xFF) / 255.0f;
            g = ((rgb >> 16) & 0xFF) / 255.0f;
            b = ((rgb >> 8) & 0xFF) / 255.0f;
        }

        tessellator.startDrawing(GL11.GL_QUADS);
        tessellator.setColorRGBA_F(r, g, b, 0.8f);

        // Single solid beam from 0 to 6 blocks high
        // Front face
        tessellator.addVertex(-animatedWidth, 0, animatedWidth);
        tessellator.addVertex(animatedWidth, 0, animatedWidth);
        tessellator.addVertex(animatedWidth, 6, animatedWidth);
        tessellator.addVertex(-animatedWidth, 6, animatedWidth);

        // Back face
        tessellator.addVertex(animatedWidth, 0, -animatedWidth);
        tessellator.addVertex(-animatedWidth, 0, -animatedWidth);
        tessellator.addVertex(-animatedWidth, 6, -animatedWidth);
        tessellator.addVertex(animatedWidth, 6, -animatedWidth);

        // Left face
        tessellator.addVertex(-animatedWidth, 0, -animatedWidth);
        tessellator.addVertex(-animatedWidth, 0, animatedWidth);
        tessellator.addVertex(-animatedWidth, 6, animatedWidth);
        tessellator.addVertex(-animatedWidth, 6, -animatedWidth);

        // Right face
        tessellator.addVertex(animatedWidth, 0, animatedWidth);
        tessellator.addVertex(animatedWidth, 0, -animatedWidth);
        tessellator.addVertex(animatedWidth, 6, -animatedWidth);
        tessellator.addVertex(animatedWidth, 6, animatedWidth);

        tessellator.draw();
        GL11.glPopMatrix();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
    }

    private static void renderSiphon(MTERealityFabricSiphon mteRealityFabricSiphon, float time) {
        float speed = (float)  mteRealityFabricSiphon.getCurrentCausalityGenerated();
        if( speed > 0 ){
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            var mc = Minecraft.getMinecraft();
            mc.getTextureManager().bindTexture(SIPHON_TEXTURE);

            GL11.glPushMatrix();
            RealityFabricSiphonRenderer.rotate(mteRealityFabricSiphon.getDirection(), mteRealityFabricSiphon.getRotation());
            GL11.glTranslated(0, 17, 2);

            int segments = 16;
            float maxHeight = 192f;
            int stepSize = 5;

            Tessellator tessellator = Tessellator.instance;
            tessellator.startDrawing(GL11.GL_TRIANGLES);

            for (int layer = 0; layer < maxHeight - stepSize; layer += stepSize) {
                float y = layer;
                float radius = calculateRadius(y);
                float nextRadius = calculateRadius(y + stepSize);

                // Color gradient from purple at bottom to white at top
                float colorFactor = y / maxHeight;

                float r = 0.5f + colorFactor * 0.5f;
                float g = 0.2f + colorFactor * 0.8f;
                float b = 0.8f + colorFactor * 0.2f;
                float alpha = 0.3f + 0.4f * (1f - colorFactor) * speed;

                for (int i = 0; i < segments; i++) {
                    float angle1 = (float) (2 * Math.PI * i / segments);
                    float angle2 = (float) (2 * Math.PI * (i + 1) / segments);

                    float swirl = (0.01f + y * 0.02f);
                    float nextSwirl = (0.01f + (y + stepSize) * 0.02f);

                    angle1 += swirl;
                    angle2 += swirl;
                    float nextAngle1 = angle1 + (nextSwirl - swirl);
                    float nextAngle2 = angle2 + (nextSwirl - swirl);

                    double cos1 = Math.cos(angle1);
                    double sin1 = Math.sin(angle1);
                    double cos2 = Math.cos(angle2);
                    double sin2 = Math.sin(angle2);
                    double nextCos1 = Math.cos(nextAngle1);
                    double nextSin1 = Math.sin(nextAngle1);
                    double nextCos2 = Math.cos(nextAngle2);
                    double nextSin2 = Math.sin(nextAngle2);

                    tessellator.setColorRGBA_F(r, g, b, alpha);

                    // Texture coordinates with downward movement
                    float texOffset = (time * 0.05f * speed) % 1.0f;
                    float u1 = (float) i / segments;
                    float u2 = (float) (i + 1) / segments;
                    float v1 = (y / maxHeight + texOffset) % 1.0f;
                    float v2 = ((y + stepSize) / maxHeight + texOffset) % 1.0f;

                    // First triangle (reversed winding for outer face)
                    tessellator.addVertexWithUV(cos1 * radius, y, sin1 * radius, u1, v1);
                    tessellator.addVertexWithUV(nextCos1 * nextRadius, y + stepSize, nextSin1 * nextRadius, u1, v2);
                    tessellator.addVertexWithUV(cos2 * radius, y, sin2 * radius, u2, v1);

                    // Second triangle (reversed winding for outer face)
                    tessellator.addVertexWithUV(cos2 * radius, y, sin2 * radius, u2, v1);
                    tessellator.addVertexWithUV(nextCos1 * nextRadius, y + stepSize, nextSin1 * nextRadius, u1, v2);
                    tessellator.addVertexWithUV(nextCos2 * nextRadius, y + stepSize, nextSin2 * nextRadius, u2, v2);
                }
            }

            tessellator.draw();

            GL11.glPopMatrix();
        }
    }

    private static float calculateRadius(float height) {
        if (height <= 0) return 1f;
        if (height <= 11) {
            return 1f + (height / 11f) * 0.5f; // 1 to 1.5 blocks
        }

        float normalizedHeight = (height - 11f) / (128f - 11f);
        float baseRadius = 1.5f + normalizedHeight * normalizedHeight * 7.5f; // Exponential to ~9 blocks

        if (normalizedHeight > 0.5f) {
            float spreadFactor = (normalizedHeight - 0.5f) / 0.5f;
            baseRadius += spreadFactor * spreadFactor * 15f;
        }

        if (normalizedHeight > 0.9f) {
            float flatFactor = (normalizedHeight - 0.9f) / 0.1f;
            baseRadius += flatFactor * 8f;
        }

        return baseRadius;
    }

    private static void rotate(ForgeDirection direction, Rotation rotation){
        switch (direction) {
            case NORTH -> {
                //GL11.glRotatef(90, 1, 0, 0);
            }
            case EAST -> {
                GL11.glRotatef(-90, 0, 1, 0);
            }
            case SOUTH -> {
                GL11.glRotatef(180, 0, 1, 0);
            }
            case WEST -> {
                GL11.glRotatef(90, 0, 1, 0);
            }
            case UP -> {
                GL11.glRotatef(180, 0, 1, 0);
                GL11.glRotatef(90, 1, 0, 0);
                GL11.glTranslatef(0,0,-0.5f);
            }
            case DOWN -> {
                GL11.glRotatef(-90, 1, 0, 0);
                GL11.glTranslatef(0,0,0.5f);

            }
        }
        if(direction.ordinal() >= 2 && rotation != Rotation.NORMAL) GL11.glTranslatef(0,0.5f,0);

        if(rotation == Rotation.NORMAL) {
            GL11.glTranslatef(0,-0.5f,0);
        }if(rotation == Rotation.CLOCKWISE) {
            GL11.glTranslatef(0.5f, 0,0);
            GL11.glRotatef(90, 0, 0, 1);
        }else if(rotation == Rotation.UPSIDE_DOWN){
            GL11.glTranslatef(0,0.5f,0);
            GL11.glRotatef(180, 0, 0, 1);
        }else if(rotation == Rotation.COUNTER_CLOCKWISE){
            GL11.glTranslatef(-0.5f, 0,0);
            GL11.glRotatef(-90, 0, 0, 1);
        }
    }

    public static void renderBoundingBoxWireframe(AxisAlignedBB bb) {
        GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glLineWidth(3);

        Tessellator tess = Tessellator.instance;
        tess.startDrawing(GL11.GL_LINES);
        tess.setColorRGBA_F(1f, 1f, 0f, 0.5f);

        // Bottom edges
        tess.addVertex(bb.minX, bb.minY, bb.minZ);
        tess.addVertex(bb.maxX, bb.minY, bb.minZ);

        tess.addVertex(bb.maxX, bb.minY, bb.minZ);
        tess.addVertex(bb.maxX, bb.minY, bb.maxZ);

        tess.addVertex(bb.maxX, bb.minY, bb.maxZ);
        tess.addVertex(bb.minX, bb.minY, bb.maxZ);

        tess.addVertex(bb.minX, bb.minY, bb.maxZ);
        tess.addVertex(bb.minX, bb.minY, bb.minZ);

        // Top edges
        tess.addVertex(bb.minX, bb.maxY, bb.minZ);
        tess.addVertex(bb.maxX, bb.maxY, bb.minZ);

        tess.addVertex(bb.maxX, bb.maxY, bb.minZ);
        tess.addVertex(bb.maxX, bb.maxY, bb.maxZ);

        tess.addVertex(bb.maxX, bb.maxY, bb.maxZ);
        tess.addVertex(bb.minX, bb.maxY, bb.maxZ);

        tess.addVertex(bb.minX, bb.maxY, bb.maxZ);
        tess.addVertex(bb.minX, bb.maxY, bb.minZ);

        // Vertical edges
        tess.addVertex(bb.minX, bb.minY, bb.minZ);
        tess.addVertex(bb.minX, bb.maxY, bb.minZ);

        tess.addVertex(bb.maxX, bb.minY, bb.minZ);
        tess.addVertex(bb.maxX, bb.maxY, bb.minZ);

        tess.addVertex(bb.maxX, bb.minY, bb.maxZ);
        tess.addVertex(bb.maxX, bb.maxY, bb.maxZ);

        tess.addVertex(bb.minX, bb.minY, bb.maxZ);
        tess.addVertex(bb.minX, bb.maxY, bb.maxZ);

        tess.draw();

        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopAttrib();
    }

}
