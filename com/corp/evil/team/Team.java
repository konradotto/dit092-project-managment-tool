import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.*;

public class Team {

    private String name;
    private ArrayList<Member> members;
    private ArrayList<Activity> activities;

    public Team(){
        members = new ArrayList<>();
        activities = new ArrayList<>();
        name = "";

    }

    public Team(String name, ArrayList<Member> members, ArrayList <Activity> activities)throws NameIsEmptyException {
        if (name.isEmpty()){ throw new NameIsEmptyException("The field name cannot be empty!"); }
        this.name = name;
        this.members = members;
        this.activities = activities;

    }


    public void addMember(Member member) throws MemberIsNullException, MemberAlreadyRegisteredException {
        if (member == null) { throw new MemberIsNullException("This member does not exist!");
        } else if (members.contains(member)) {
            throw new MemberAlreadyRegisteredException("The member is already registered!");
        } else {
            members.add(member);
        }
    }


    public void addMembers(List<Member> members)throws MemberIsNullException, MemberAlreadyRegisteredException{
        for (Member member :members){
            addMember(member);
        }

    }

    public void removeMember(Member member)throws MemberIsNullException {
        if (member == null) { throw new MemberIsNullException("This member does not exist!"); }
        members.remove(member);
    }

    public void addActivity(Activity activity) throws ActivityAlreadyRegisteredException, ActivityIsNullException{
        if (activity == null) { throw new ActivityIsNullException("This activity does not exist!");
        } else if (activities.contains(activity)) {
            throw new ActivityAlreadyRegisteredException("An activity with same name exists already!");
        } else {
            activities.add(activity);
        }
    }



    public void addActivity(List<Activity> activities)throws ActivityAlreadyRegisteredException, ActivityIsNullException {
        for (Activity activity : activities) {
            addActivity(activity);
        }
    }



    public void removeActivity(Activity activity) throws ActivityIsNullException {
        if (activity == null){ throw new ActivityIsNullException("This activity does not exist!");}
        activities.remove(activity);
    }

    public boolean teamContains(Member member){
        return members.contains(member);
    }

    public Member retrieveMember (Member member){
        Member result = null;

        for(Member temp : members) {
            if (temp.equals(member)) {return temp;}
        }

        return result;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "Team{" +
                "name='" + name + '\'' +
                ", members=" + members +
                ", activities=" + activities +
                '}';
    }

    //GETTERS AND SETTERS
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}







    public List<Member> readJsonStreamMembers(Gson gson , InputStream in) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        List<Member> members = new ArrayList<Member>();
        reader.beginArray();
        while (reader.hasNext()) {
            Member member = gson.fromJson(reader, Member.class);
            members.add(member);
        }
        reader.endArray();
        reader.close();
        return members;
    }


    static void prettyprint(JsonReader reader, JsonWriter writer) throws IOException {
        while (true) {
            JsonToken token = reader.peek();
            switch (token) {
                case BEGIN_ARRAY:
                    reader.beginArray();
                    writer.beginArray();
                    break;
                case END_ARRAY:
                    reader.endArray();
                    writer.endArray();
                    break;
                case BEGIN_OBJECT:
                    reader.beginObject();
                    writer.beginObject();
                    break;
                case END_OBJECT:
                    reader.endObject();
                    writer.endObject();
                    break;
                case NAME:
                    String name = reader.nextName();
                    writer.name(name);
                    break;
                case STRING:
                    String s = reader.nextString();
                    writer.value(s);
                    break;
                case NUMBER:
                    String n = reader.nextString();
                    writer.value(new BigDecimal(n));
                    break;
                case BOOLEAN:
                    boolean b = reader.nextBoolean();
                    writer.value(b);
                    break;
                case NULL:
                    reader.nextNull();
                    writer.nullValue();
                    break;
                case END_DOCUMENT:
                    return;
            }
        }
    }

}
