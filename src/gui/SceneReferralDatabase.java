package gui;

import java.util.HashMap;

public class SceneReferralDatabase {
	private HashMap<String, SceneBuilder> sceneReferralDatabase;
	
	public SceneReferralDatabase() {
		this.sceneReferralDatabase = new HashMap<>();
	}
	
	public void addSceneReferral(String string, SceneBuilder sceneBuilder) {
		sceneReferralDatabase.put(string, sceneBuilder);
	}
	
	public SceneBuilder getSceneBuilder(String string) {
		return sceneReferralDatabase.get(string);
	}
	
	// TODO add something to ensure each key has a value
	// TODO add exception handling for 404 key
}
