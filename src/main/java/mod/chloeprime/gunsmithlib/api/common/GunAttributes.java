package mod.chloeprime.gunsmithlib.api.common;

import mod.chloeprime.gunsmithlib.GunsmithLib;
import mod.chloeprime.gunsmithlib.common.util.PercentBasedAttribute;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.neoforged.neoforge.registries.DeferredHolder;

public class GunAttributes {
    /**
     * 射击伤害
     */
    public static final DeferredHolder<Attribute, RangedAttribute> BULLET_DAMAGE = GunsmithLib.Attributes.BULLET_DAMAGE;

    /**
     * 子弹速度
     */
    public static final DeferredHolder<Attribute, RangedAttribute> BULLET_SPEED = GunsmithLib.Attributes.BULLET_SPEED;

    /**
     * 垂直后坐力
     */
    public static final DeferredHolder<Attribute, PercentBasedAttribute> V_RECOIL = GunsmithLib.Attributes.V_RECOIL;

    /**
     * 水平后坐力
     */
    public static final DeferredHolder<Attribute, PercentBasedAttribute> H_RECOIL = GunsmithLib.Attributes.H_RECOIL;
}
