package gregtech.api.util;

import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;

import com.gtnewhorizon.structurelib.alignment.enumerable.Rotation;

public class GTBoundingBoxUtil {

    private static final double[] ROT_ANGLES = {
        0.0,                // NORMAL
        Math.PI / 2,        // CLOCKWISE
        Math.PI,            // UPSIDE_DOWN
        -Math.PI / 2        // COUNTER_CLOCKWISE
    };

    private GTBoundingBoxUtil() {}

    /**
     * Copy bounding box with offset
     * @return
     */
    public static AxisAlignedBB withOffset(AxisAlignedBB bb, int x, int y ,int z){
        return AxisAlignedBB.getBoundingBox(
            bb.minX+x, bb.minY+y, bb.minZ+z,
            bb.maxX+x, bb.maxY+y, bb.maxZ+z
        );
    }

    /**
     * Precomputes all rotated bounding boxes.
     *
     * @param base     base AABB
     * @param pivotX   rotation pivot X
     * @param pivotY   rotation pivot Y
     * @param pivotZ   rotation pivot Z
     */
    public static AxisAlignedBB[][] precomputeAABB(AxisAlignedBB base, double pivotX, double pivotY, double pivotZ) {
        AxisAlignedBB offsetBB = AxisAlignedBB.getBoundingBox(
            base.minX-pivotX, base.minY-pivotY, base.minZ-pivotZ,
            base.maxX-pivotX, base.maxY-pivotY, base.maxZ-pivotZ
        );
        AxisAlignedBB[][] result = new AxisAlignedBB[ForgeDirection.VALID_DIRECTIONS.length][Rotation.VALUES.length];
        for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
            for (Rotation rot : Rotation.VALUES) {
                AxisAlignedBB bb = applyDirection(offsetBB, dir);
                bb = rotateBox(bb, dir, rot);

                result[dir.ordinal()][rot.getIndex()] = bb.offset(pivotX, pivotY, pivotZ);
            }
        }

        return result;
    }

    private static AxisAlignedBB applyDirection(AxisAlignedBB bb, ForgeDirection dir) {
        return switch (dir) {
            case SOUTH -> AxisAlignedBB.getBoundingBox(
                1 - bb.maxX, bb.minY, 1 - bb.maxZ,
                1 - bb.minX, bb.maxY, 1 - bb.minZ
            );
            case WEST -> AxisAlignedBB.getBoundingBox(
                bb.minZ, bb.minY, 1 - bb.maxX,
                bb.maxZ, bb.maxY, 1 - bb.minX
            );
            case EAST -> AxisAlignedBB.getBoundingBox(
                1 - bb.maxZ, bb.minY, bb.minX,
                1 - bb.minZ, bb.maxY, bb.maxX
            );
            case UP -> AxisAlignedBB.getBoundingBox(
                bb.minX, 1 - bb.maxZ, bb.minY,
                bb.maxX, 1 - bb.minZ, bb.maxY
            );
            case DOWN -> AxisAlignedBB.getBoundingBox(
                bb.minX, bb.minZ, bb.minY,
                bb.maxX, bb.maxZ, bb.maxY
            );
            default -> bb; // NORTH
        };
    }

    private static AxisAlignedBB rotateBox(AxisAlignedBB bb, ForgeDirection direction, Rotation rot) {
        double angle = ROT_ANGLES[rot.getIndex()];
        double rad = Math.toRadians(angle);

        // Original 8 vertices
        double[][] verts = new double[][]{
            {bb.minX, bb.minY, bb.minZ},
            {bb.minX, bb.minY, bb.maxZ},
            {bb.minX, bb.maxY, bb.minZ},
            {bb.minX, bb.maxY, bb.maxZ},
            {bb.maxX, bb.minY, bb.minZ},
            {bb.maxX, bb.minY, bb.maxZ},
            {bb.maxX, bb.maxY, bb.minZ},
            {bb.maxX, bb.maxY, bb.maxZ},
        };

        double minX = Double.POSITIVE_INFINITY, minY = Double.POSITIVE_INFINITY, minZ = Double.POSITIVE_INFINITY;
        double maxX = Double.NEGATIVE_INFINITY, maxY = Double.NEGATIVE_INFINITY, maxZ = Double.NEGATIVE_INFINITY;

        for (double[] v : verts) {
            double x = v[0], y = v[1], z = v[2];
            double rx = x, ry = y, rz = z;

            switch (direction) {
                case NORTH, SOUTH -> { // rotate around Z
                    rx = x * Math.cos(rad) - y * Math.sin(rad);
                    ry = x * Math.sin(rad) + y * Math.cos(rad);
                }
                case EAST, WEST -> { // rotate around X
                    ry = y * Math.cos(rad) - z * Math.sin(rad);
                    rz = y * Math.sin(rad) + z * Math.cos(rad);
                }
                default -> { // rotate around Y
                    rx = x * Math.cos(rad) + z * Math.sin(rad);
                    rz = -x * Math.sin(rad) + z * Math.cos(rad);
                }
            }

            minX = Math.min(minX, rx);
            minY = Math.min(minY, ry);
            minZ = Math.min(minZ, rz);
            maxX = Math.max(maxX, rx);
            maxY = Math.max(maxY, ry);
            maxZ = Math.max(maxZ, rz);
        }

        return AxisAlignedBB.getBoundingBox(minX, minY, minZ, maxX, maxY, maxZ);
    }

}
