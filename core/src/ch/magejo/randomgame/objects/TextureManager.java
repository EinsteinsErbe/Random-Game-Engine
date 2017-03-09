package ch.magejo.randomgame.objects;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;

public class TextureManager {
	private static ArrayList<Texture> textures = new ArrayList<>();
	private static ArrayList<String> paths = new ArrayList<>();
	
	public static int addTexture(String path){
		if(!paths.contains(path)){
			textures.add(new Texture(path));
		}else{
			return paths.indexOf(path);
		}
		
		return textures.size()-1;
	}
	
	public static Texture getTexture(int i){
		return textures.get(i);
	}	
}
