package rouletteores.client;

import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
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
	
	public void DrawBox(Vec3 pos, Vec3 size, Vec3 rot, Color color)
	{
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        
        tessellator.setColorRGBA_F(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
        
        Vec3[] verts = new Vec3[8];
        
        for(int v = 0; v < verts.length; v++)
        {
        	Vec3 vec = Vec3.createVectorHelper(-size.xCoord/2F, -size.yCoord/2F, -size.zCoord/2F);
        	
        	vec.xCoord *= (v & 1) == 1? -1D : 1D;
        	vec.yCoord *= (v & 2) == 2? -1D : 1D;
        	vec.zCoord *= (v & 4) == 4? -1D : 1D;
        	
        	vec.rotateAroundX((float)rot.xCoord);
        	vec.rotateAroundY((float)rot.yCoord);
        	vec.rotateAroundZ((float)rot.zCoord);
        	
        	vec.xCoord += pos.xCoord;
        	vec.yCoord += pos.yCoord;
        	vec.zCoord += pos.zCoord;
        	
        	verts[v] = vec;
        }
        
        tessellator.addVertexWithUV(verts[2].xCoord, verts[2].yCoord, verts[2].zCoord, 0D, 0D);
        tessellator.addVertexWithUV(verts[6].xCoord, verts[6].yCoord, verts[6].zCoord, 0D, 1D);
        tessellator.addVertexWithUV(verts[7].xCoord, verts[7].yCoord, verts[7].zCoord, 1D, 1D);
        tessellator.addVertexWithUV(verts[3].xCoord, verts[3].yCoord, verts[3].zCoord, 1D, 0D);
        
        tessellator.addVertexWithUV(verts[1].xCoord, verts[1].yCoord, verts[1].zCoord, 1D, 0D);
        tessellator.addVertexWithUV(verts[5].xCoord, verts[5].yCoord, verts[5].zCoord, 1D, 1D);
        tessellator.addVertexWithUV(verts[4].xCoord, verts[4].yCoord, verts[4].zCoord, 0D, 1D);
        tessellator.addVertexWithUV(verts[0].xCoord, verts[0].yCoord, verts[0].zCoord, 0D, 0D);
        
        tessellator.addVertexWithUV(verts[0].xCoord, verts[0].yCoord, verts[0].zCoord, 0D, 1D);
        tessellator.addVertexWithUV(verts[4].xCoord, verts[4].yCoord, verts[4].zCoord, 1D, 1D);
        tessellator.addVertexWithUV(verts[6].xCoord, verts[6].yCoord, verts[6].zCoord, 1D, 0D);
        tessellator.addVertexWithUV(verts[2].xCoord, verts[2].yCoord, verts[2].zCoord, 0D, 0D);
        
        tessellator.addVertexWithUV(verts[3].xCoord, verts[3].yCoord, verts[3].zCoord, 1D, 0D);
        tessellator.addVertexWithUV(verts[7].xCoord, verts[7].yCoord, verts[7].zCoord, 0D, 0D);
        tessellator.addVertexWithUV(verts[5].xCoord, verts[5].yCoord, verts[5].zCoord, 0D, 1D);
        tessellator.addVertexWithUV(verts[1].xCoord, verts[1].yCoord, verts[1].zCoord, 1D, 1D);
        
        tessellator.addVertexWithUV(verts[2].xCoord, verts[2].yCoord, verts[2].zCoord, 1D, 0D);
        tessellator.addVertexWithUV(verts[3].xCoord, verts[3].yCoord, verts[3].zCoord, 0D, 0D);
        tessellator.addVertexWithUV(verts[1].xCoord, verts[1].yCoord, verts[1].zCoord, 0D, 1D);
        tessellator.addVertexWithUV(verts[0].xCoord, verts[0].yCoord, verts[0].zCoord, 1D, 1D);
        
        tessellator.addVertexWithUV(verts[4].xCoord, verts[4].yCoord, verts[4].zCoord, 0D, 1D);
        tessellator.addVertexWithUV(verts[5].xCoord, verts[5].yCoord, verts[5].zCoord, 1D, 1D);
        tessellator.addVertexWithUV(verts[7].xCoord, verts[7].yCoord, verts[7].zCoord, 1D, 0D);
        tessellator.addVertexWithUV(verts[6].xCoord, verts[6].yCoord, verts[6].zCoord, 0D, 0D);
        
        tessellator.draw();
	}
}
