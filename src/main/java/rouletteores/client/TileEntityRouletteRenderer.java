package rouletteores.client;

import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import org.lwjgl.opengl.GL11;

public class TileEntityRouletteRenderer extends TileEntitySpecialRenderer
{
    private static final IModelCustom box = AdvancedModelLoader.loadModel(new ResourceLocation("rouletteores", "models/box.obj"));
    private static final ResourceLocation mask = new ResourceLocation("rouletteores", "textures/misc/mask.png");
    private static final ResourceLocation skull = new ResourceLocation("rouletteores", "textures/misc/skull.png");
    private static final ResourceLocation stoneTex = new ResourceLocation("textures/blocks/stone.png");
    
	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float partialTick)
	{
		float color = (Minecraft.getSystemTime()/5000F) + ((float)x%16F + (float)z%16 + (float)y%16F)/12F;
		float alpha = MathHelper.clamp_float(MathHelper.sin((Minecraft.getSystemTime()%1000000F)/500000F * 360F), 0F, 1F);
		
		Color hsb = Color.getHSBColor(color, 1F, 1F);
		GL11.glPushMatrix();
		
		GL11.glTranslated(x + 0.5D, y + 0.5D, z + 0.5D);
		this.bindTexture(stoneTex);
		box.renderAll();
		
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glScalef(1.001F, 1.001F, 1.001F);
		GL11.glColor4f(hsb.getRed()/255F, hsb.getGreen()/255F, hsb.getBlue()/255F, 1F);
		this.bindTexture(mask);
		box.renderAll();
		
		GL11.glScalef(1.001F, 1.001F, 1.001F);
		GL11.glColor4f(1F, 1F, 1F, alpha);
		this.bindTexture(skull);
		box.renderAll();
		GL11.glDisable(GL11.GL_BLEND);
        
        GL11.glPopMatrix();
    }
}
