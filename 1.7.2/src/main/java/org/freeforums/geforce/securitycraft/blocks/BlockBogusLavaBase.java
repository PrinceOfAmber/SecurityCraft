package org.freeforums.geforce.securitycraft.blocks;

import java.util.Random;

import org.freeforums.geforce.securitycraft.main.mod_SecurityCraft;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStaticLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockBogusLavaBase extends BlockStaticLiquid
{

    public BlockBogusLavaBase(Material p_i45429_1_)
    {
        super(p_i45429_1_);
        this.setTickRandomly(false);

        if (p_i45429_1_ == Material.lava)
        {
            this.setTickRandomly(true);
        }
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor Block
     */
    public void onNeighborBlockChange(World p_149695_1_, int p_149695_2_, int p_149695_3_, int p_149695_4_, Block p_149695_5_)
    {
        //super.onNeighborBlockChange(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_, p_149695_5_);

        if (p_149695_1_.getBlock(p_149695_2_, p_149695_3_, p_149695_4_) == this)
        {
            this.setNotStationary(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_);
        }
    }

    /**
     * Changes the block ID to that of an updating fluid.
     */
    private void setNotStationary(World p_149818_1_, int p_149818_2_, int p_149818_3_, int p_149818_4_)
    {
        int l = p_149818_1_.getBlockMetadata(p_149818_2_, p_149818_3_, p_149818_4_);
        p_149818_1_.setBlock(p_149818_2_, p_149818_3_, p_149818_4_, mod_SecurityCraft.bogusLavaFlowing, l, 2);
        p_149818_1_.scheduleBlockUpdate(p_149818_2_, p_149818_3_, p_149818_4_, mod_SecurityCraft.bogusLavaFlowing, this.tickRate(p_149818_1_));
    }

    /**
     * Ticks the block if it's been scheduled
     */
    public void updateTick(World p_149674_1_, int p_149674_2_, int p_149674_3_, int p_149674_4_, Random p_149674_5_)
    {
        if (this.blockMaterial == Material.lava)
        {
            int l = p_149674_5_.nextInt(3);
            int i1;

            for (i1 = 0; i1 < l; ++i1)
            {
                p_149674_2_ += p_149674_5_.nextInt(3) - 1;
                ++p_149674_3_;
                p_149674_4_ += p_149674_5_.nextInt(3) - 1;
                Block block = p_149674_1_.getBlock(p_149674_2_, p_149674_3_, p_149674_4_);

                if (block.getMaterial() == Material.air)
                {
                    if (this.isFlammable(p_149674_1_, p_149674_2_ - 1, p_149674_3_, p_149674_4_) || this.isFlammable(p_149674_1_, p_149674_2_ + 1, p_149674_3_, p_149674_4_) || this.isFlammable(p_149674_1_, p_149674_2_, p_149674_3_, p_149674_4_ - 1) || this.isFlammable(p_149674_1_, p_149674_2_, p_149674_3_, p_149674_4_ + 1) || this.isFlammable(p_149674_1_, p_149674_2_, p_149674_3_ - 1, p_149674_4_) || this.isFlammable(p_149674_1_, p_149674_2_, p_149674_3_ + 1, p_149674_4_))
                    {
                        p_149674_1_.setBlock(p_149674_2_, p_149674_3_, p_149674_4_, Blocks.fire);
                        return;
                    }
                }
                else if (block.getMaterial().blocksMovement())
                {
                    return;
                }
            }

            if (l == 0)
            {
                i1 = p_149674_2_;
                int k1 = p_149674_4_;

                for (int j1 = 0; j1 < 3; ++j1)
                {
                    p_149674_2_ = i1 + p_149674_5_.nextInt(3) - 1;
                    p_149674_4_ = k1 + p_149674_5_.nextInt(3) - 1;

                    if (p_149674_1_.isAirBlock(p_149674_2_, p_149674_3_ + 1, p_149674_4_) && this.isFlammable(p_149674_1_, p_149674_2_, p_149674_3_, p_149674_4_))
                    {
                        p_149674_1_.setBlock(p_149674_2_, p_149674_3_ + 1, p_149674_4_, Blocks.fire);
                    }
                }
            }
        }
    }
    
    /**
     * Triggered whenever an entity collides with this block (enters into the block). Args: world, x, y, z, entity
     */
   public void onEntityCollidedWithBlock(World par1World, int par2, int par3, int par4, Entity par5Entity)
   {
    	if(!par1World.isRemote){
    		
    		if(par5Entity instanceof EntityPlayer){
    			((EntityPlayer) par5Entity).heal(4);
    			((EntityPlayer) par5Entity).extinguish();
    		}
    	}
   }

    /**
     * Checks to see if the block is flammable.
     */
    private boolean isFlammable(World p_149817_1_, int p_149817_2_, int p_149817_3_, int p_149817_4_)
    {
        return p_149817_1_.getBlock(p_149817_2_, p_149817_3_, p_149817_4_).getMaterial().getCanBurn();
    }
    
    /**
     * Gets an item for the block being called on. Args: world, x, y, z
     */
    @SideOnly(Side.CLIENT)
    public Item getItem(World p_149694_1_, int p_149694_2_, int p_149694_3_, int p_149694_4_)
    {
        return null;
    }
}