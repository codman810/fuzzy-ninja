import java.util.ArrayList;
import java.util.Date;


public class Task {
	//Name
	String Name = new String();
	//Time Due
	long due;
	//Subtasks
	ArrayList<Task> tasks = new ArrayList<Task>();
	int id = 0, numTasks = 0;
	//% done
	double numDone = 0;
	float percentage;
	boolean completed;
	//notes
	ArrayList<String> notes = new ArrayList<String>();
	//Defcon
	int defcon;
	//Location
	//Alarm
	
	//Constructors
	public Task(){
		this.Name = "You should put something here...";
		this.due = 0;
		this.defcon = 2;
		this.percentage = 0;
	}
	
	public Task(String name){
		this.Name = name;
		this.percentage = 0;
	}
	
	
	//Setters
	public void setName(String name){
		this.Name = name;
	}
	
	public void setDefcon(int defcon){
		this.defcon = defcon;
	}
	
	public void setDue(long due){
		this.due = due;
	}
	
	public void addNote(String note){
		this.notes.add(note);
	}
	
	public void addSubTask(Task sub){
		if(id < 2){
			numTasks++;
			sub.id = this.id+1;
			this.tasks.add(sub);
		}
	}
	
	public void toggleComplete(){
		this.completed = !this.completed;
	}
	
	
	//Getters
	public Date timeLeft(){
		long now = System.currentTimeMillis();
		Date d;
		if(now > due){
			d = new Date(now-due);
		}
		else{
			d = null;
		}
		return d;
	}
	
	public boolean completed(){
		return this.completed;
	}
	
	public int getDefcon(){
		return this.defcon;
	}
	
	public String getName(){
		return this.Name;
	}
	
	public ArrayList<Task> getTasks(){
		return this.tasks;
	}
	
	public float percentComplete(){
		return this.numTasks == 0?0:(float)(this.numDone/this.numTasks);
	}
	
	public int getId(){
		return this.id;
	}
	
}
