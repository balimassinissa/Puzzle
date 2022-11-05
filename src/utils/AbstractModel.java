package utils;

import java.util.ArrayList;


public abstract class AbstractModel implements ObservableModel {
	protected ArrayList<ObserverModel> listObserver = new ArrayList<ObserverModel>();
	public void addObserver(ObserverModel e) {
		this.listObserver.add(e);
	}
	public void removeObserver(ObserverModel e) {
		this.listObserver.remove(e);
	}
	public void fireChange() {
		for (ObserverModel e : listObserver)
			e.updateModel();
	}
	public void fireChange(int value) {
		for (ObserverModel e : listObserver)
			e.updateModel(value);
	}
	
}
