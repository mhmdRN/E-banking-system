package models;

public interface TransNotification {
	public void register(Observer o);
	public void unregister(Observer o);
	public void notifyObservers();
}
