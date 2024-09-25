package mod.chloeprime.gunsmithlib.proxies;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.loading.FMLLoader;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;

class ClientProxyImpl {
    private static final Minecraft MC = Minecraft.getInstance();
    private static final PoseStack POSE = new PoseStack();

    static Vec3 bobCompensation(Vec3 original) {
        var player = MC.player;
        if (player == null) {
            return original;
        }

        var pPartialTicks = MC.getPartialTick();
        float f = player.walkDist - player.walkDistO;
        float f1 = -(player.walkDist + f * pPartialTicks);
        float f2 = Mth.lerp(pPartialTicks, player.oBob, player.bob);
        Matrix4f pose;
        POSE.pushPose();
        {
            POSE.translate(Mth.sin(f1 * (float) Math.PI) * f2 * 0.5F, -Math.abs(Mth.cos(f1 * (float) Math.PI) * f2), 0.0F);
            POSE.mulPose(Axis.ZP.rotationDegrees(Mth.sin(f1 * (float) Math.PI) * f2 * 3.0F));
            POSE.mulPose(Axis.XP.rotationDegrees(Math.abs(Mth.cos(f1 * (float) Math.PI - 0.2F) * f2) * 5.0F));
            pose = POSE.last().pose();
        }
        POSE.popPose();

        var affineVec = new Vector4f((float) original.x, (float) original.y, (float) original.z, 1);
        var transformed = pose.transform(affineVec);
        if (transformed.w == 0) {
            return original;
        }
        transformed.div(transformed.w);
        return new Vec3(transformed.x, transformed.y, transformed.z);
    }
}
