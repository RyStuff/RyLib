package me.thegeekyguy101.RyLib;

import java.io.File;
import java.util.Map;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin.MCVersion;

@MCVersion(value = "1.6.4")
public class RyLibFMLLoadingPlugin implements cpw.mods.fml.relauncher.IFMLLoadingPlugin {
	
	public static File location;

	@Override
	public String[] getASMTransformerClass() {
		//return null;
		return new String[]{RyLibClassTransformer.class.getName()};
	}

	@Override
	public String getModContainerClass() {
		return RyLibDummyContainer.class.getName();
	}

	@Override
	public void injectData(Map<String, Object> data) {
		location = (File) data.get("coremodLocation");
	}

	@Override
	public String[] getLibraryRequestClass() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSetupClass() {
		// TODO Auto-generated method stub
		return null;
	}

}
