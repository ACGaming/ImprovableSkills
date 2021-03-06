package com.endie.is.items;

import com.endie.is.api.PlayerSkillData;
import com.endie.is.data.PlayerDataManager;
import com.endie.is.net.PacketOpenSkillsBook;
import com.pengu.hammercore.net.HCNetwork;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemSkillsBook extends Item
{
	public ItemSkillsBook()
	{
		setUnlocalizedName("skills_book");
		setMaxStackSize(1);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
	{
		if(!worldIn.isRemote && playerIn instanceof EntityPlayerMP)
			HCNetwork.manager.sendTo(new PacketOpenSkillsBook(PlayerDataManager.getDataFor(playerIn)), (EntityPlayerMP) playerIn);
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}
	
	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
	{
		if(entityIn instanceof EntityPlayerMP && !worldIn.isRemote)
		{
			PlayerSkillData d = PlayerDataManager.getDataFor((EntityPlayerMP) entityIn);
			if(d != null)
				d.hasCraftedSkillBook = true;
		}
		super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
	}
}