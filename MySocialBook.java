import java.io.*;
import java.util.ArrayList;
import java.util.Date;

public class MySocialBook {

    public static void main (String[] args) throws IOException {
        ArrayList<String> users = new ArrayList<String>();//users list
        ArrayList<ArrayList<String>> posts = new ArrayList<ArrayList<String>>(); //list of nested posts
        FileReader usersTxt = new FileReader("users.txt");
        String line;
        int userID = 1; //Assigning a variable to give users a private ID.
        BufferedReader br = new BufferedReader(usersTxt);
        while ((line = br.readLine()) != null) {
            String[] usersInfo = line.split("\t"); //We separate the lines with \t, sync each information to the information in the user class, and add it to the users list.
            user User = new user();
            User.userID = userID;
            User.name = usersInfo[0];
            User.username = usersInfo[1];
            User.password = usersInfo[2];
            User.dateofBirth = usersInfo[3];
            User.school = usersInfo[4];
            userID++;
            users.add(User.userID+"\t"+User.name+"\t"+User.username+"\t"+User.password+"\t"+User.dateofBirth+"\t"+User.school);
        }
        br.close();

        FileReader commandTxt = new FileReader("commands.txt");
        String line1;
        int line_count = 0;
        int commandCount = 0;/*line_count and commandCount are values created to check whether input has been made.
        They'll increase one by one in the while cycle. the commandCount variable of the line 'sign in' calculates the ordinal number of other commands given to the commandCount variable.
        If the queue number of the log on line increases from other commands, it gives an error.*/
        boolean signin = false;//It checks whether we should print the 'SHOWPOSTS' command after logging in or print without logging in.
        BufferedReader br1 = new BufferedReader(commandTxt);

        while ((line1 = br1.readLine()) != null) {
            line_count++;// raise for 'sign in'.
            ArrayList<ArrayList<String>> usersList = new ArrayList<ArrayList<String>>(); //Here, we place users ' information in a nested list.
            // The first element of the list is 1. it's becoming a user. List 1. inside its element is the information of that user.
            for(int i = 0; i<users.size();i++){
                ArrayList<String> Userss = new ArrayList<String>();
                usersList.add(Userss);
                String[] y = users.get(i).split("\t");
                for(int l = 0; l < 6;l++){
                    usersList.get(i).add(y[l]);
                }
            }
            if (line1.contains("ADDUSER")) {//We add the new user to the users list.
                String[] a = line1.split("\t");
                user User = new user();
                User.userID = userID;
                User.name = a[1];
                User.username = a[2];
                User.password = a[3];
                User.dateofBirth = a[4];
                User.school = a[5];
                userID++;
                users.add(User.userID+"\t"+User.name+"\t"+User.username+"\t"+User.password+"\t"+User.dateofBirth+"\t"+User.school);
                System.out.println("-----------------------");
                System.out.println("Command:\t"+line1);
                System.out.println(User.name+" has been successfully added.");



            }
            else if (line1.contains("REMOVEUSER")) {//We remove the desired user from the 'users' list.
                String[] a = line1.split("\t");
                int o = users.size();
                for(int i = 0;i<users.size();i++) {
                    String k = users.get(i);
                    String[] b = k.split("\t");
                    if (a[1].equals(b[0])){//We find the user with the user's user number and remove it from the' users ' list.
                        users.remove(i);
                        i--;//We're reducing the 'i' so it doesn't out of the list.
                        System.out.println("-----------------------");
                        System.out.println("Command:\t"+line1);
                        System.out.println("User has been successfully removed.");
                    }
                }if(o == users.size()){
                    System.out.println("-----------------------");
                    System.out.println("Command:\t"+line1);
                    System.out.println("No such user!");
                }


            }
            else if (line1.contains("SHOWPOSTS")) {//We print the desired user's posts
                if (!signin) {////It checks whether we should print the SHOWPOSTS command after logging in or print without logging in.
                    System.out.println("-----------------------");
                    System.out.println("Command:\t" + line1);
                    String[] a = line1.split("\t");
                    boolean userspost = false;
                    for (int h = 0; h < usersList.size(); h++) {
                        if (a[1].equals(usersList.get(h).get(2))) {//We check whether the desired user.
                            userspost = true;
                            if (posts.size() == 0) {
                                System.out.println(a[1] + " does not have any posts yet.");
                            } else {
                                System.out.println("**************\n" +
                                        a[1] + "'s Posts\n" +
                                        "**************");
                                for (int post = 0; post < posts.size(); post++) {
                                    for (int j = 0; j < posts.get(post).size(); j++) {
                                        System.out.println(posts.get(post).get(j));

                                    }
                                    System.out.println("-----------------------");
                                }
                            }
                        }
                    }
                    if (!userspost) {
                        System.out.println("No such user!");
                        System.out.println(usersList.get(0).get(2));
                    }

                }


            }
            else if(line1.contains("SIGNOUT")) {
                signin = false;



            }
            else if (line1.contains("SIGNIN")){//we're signing in.
                Date log_in_date = new Date();//The date the user last logged in.
                String[] a = line1.split("\t");
                int count = 0;
                int postCounter = 0;
                signin = true;
                for(int i=0;i<usersList.size();i++){
                    if (a[1].equals(usersList.get(i).get(2))  &&  a[2].equals(usersList.get(i).get(3))) {//We're checking the accuracy of the password and username.
                        System.out.println("-----------------------");
                        System.out.println("Command:\t"+line1);
                        System.out.println("You have successfully signed in.");
                        count++;
                        ArrayList<String> friendsList = new ArrayList<String>();
                        ArrayList<String> blockusers = new ArrayList<String>();
                        FileReader commandTxt2 = new FileReader("commands.txt");
                        String line2;
                        BufferedReader br2 = new BufferedReader(commandTxt2);
                        while ((line2 = br2.readLine()) != null) {
                            commandCount++;


                            if (line2.contains("SIGNOUT")) {//Signing out.
                                System.out.println("-----------------------");
                                System.out.println("Command:\t" + line2);
                                System.out.println("You have successfully signed out.");
                                break;




                            }
                            else if (line2.contains("SHOWPOSTS")) {//We print the desired user's posts
                                if(line_count > commandCount){//We're looking at whether the command was given to us before or after signing in.
                                    continue;
                                }else {
                                    System.out.println("-----------------------");
                                    System.out.println("Command:\t" + line2);
                                    String[] oo = line2.split("\t");
                                    boolean userspost = false;
                                    for (int h = 0; h < usersList.size(); h++) {
                                        if (oo[1].equals(usersList.get(h).get(2))) {//We check whether the desired user.
                                            userspost = true;
                                            if (posts.size() == 0) {
                                                System.out.println(oo[1] + " does not have any posts yet.");
                                            } else {
                                                System.out.println("**************\n" +
                                                        oo[1] + "'s Posts\n" +
                                                        "**************");
                                                for (int post = 0; post < posts.size(); post++) {
                                                    for (int j = 0; j < posts.get(post).size(); j++) {
                                                        System.out.println(posts.get(post).get(j));

                                                    }
                                                    System.out.println("-----------------------");
                                                }
                                            }
                                        }
                                    }
                                    if (!userspost) {
                                        System.out.println("No such user!");
                                        System.out.println(usersList.get(0).get(2));
                                    }
                                }







                            }
                            else if (line2.contains("CHPASS")) {//We use this code to change the password.
                                if(line_count > commandCount){//We're looking at whether the command was given to us before or after signing in.
                                    continue;
                                }else {
                                    boolean chpassPassword = false;
                                    String[] chpass = line2.split("\t");
                                    for(int w=0;w<usersList.size();w++){
                                        if(chpass[1].equals(usersList.get(w).get(3))){//We're checking to see if the password is correct.
                                            usersList.get(w).set(3,chpass[2]);
                                            chpassPassword = true;
                                            System.out.println("-----------------------");
                                            System.out.println("Command:\t"+line2);
                                        }
                                    }if (!chpassPassword){
                                        System.out.println("-----------------------");
                                        System.out.println("Command:\t"+line2);
                                        System.out.println("Password mismatch!");
                                    }
                                }



                            }
                            else if (line2.contains("UPDATEPROFILE")) {//We use this code to update profile.
                                String[] update = line2.split("\t");
                                if(line_count > commandCount){//We're looking at whether the command was given to us before or after signing in.
                                    continue;
                                }else{
                                    usersList.get(i).set(1,update[1]);
                                    usersList.get(i).set(4,update[2]);
                                    usersList.get(i).set(5,update[3]);
                                    System.out.println("-----------------------");
                                    System.out.println("Command:\t"+line2);
                                    System.out.println("Your user profile has been successfully updated");
                                }




                            }
                            else if (line2.contains("ADDFRIEND")) {//We use this code to add friend.
                                if(line_count > commandCount){//We're looking at whether the command was given to us before or after signing in.
                                    continue;
                                }else {
                                    String[] addfriend = line2.split("\t");
                                    boolean e = false;
                                    for(int w=0;w<usersList.size();w++){
                                        if (usersList.get(w).get(2).equals(addfriend[1])){//The user name in the given command is searched in the 'usersList' list.
                                            e = true;
                                            int oldFriendsListSize = friendsList.size();
                                            if (oldFriendsListSize == 0) {
                                                friendsList.add(addfriend[1]);
                                                System.out.println("-----------------------");
                                                System.out.println("Command:\t" + line2);
                                                System.out.println(addfriend[1] + " has been successfully added to your friend list.");
                                            }else {
                                                boolean z = false;
                                                for (int t = 0; t < oldFriendsListSize; t++) {
                                                    if (friendsList.get(t).equals(addfriend[1])) {//We check whether the user name in the given command is in the friends list.
                                                        System.out.println("-----------------------");
                                                        System.out.println("Command:\t" + line2);
                                                        System.out.println("This user is already in your friend list!");
                                                        z = true;
                                                    }
                                                }if(!z){
                                                    friendsList.add(addfriend[1]);
                                                    System.out.println("-----------------------");
                                                    System.out.println("Command:\t" + line2);
                                                    System.out.println(addfriend[1] + " has been successfully added to your friend list.");
                                                }
                                            }
                                        }

                                    }if (!e){
                                        System.out.println("-----------------------");
                                        System.out.println("Command:\t"+line2);
                                        System.out.println("No such user!");
                                    }
                                }



                            }
                            else if (line2.contains("REMOVEFRIEND")) {//We use this code to remove friend.
                                if(line_count > commandCount){//We're looking at whether the command was given to us before or after signing in.
                                    continue;
                                }else {
                                    String[] removeFriend = line2.split("\t");
                                    boolean tt = false;
                                    boolean ss = false;
                                    for (int ab = 0; ab < usersList.size();ab++){
                                        if (removeFriend[1].equals(usersList.get(ab).get(2))){//We check whether the user name given in the command is in the user list.
                                            for (int aa = 0; aa < friendsList.size();aa++){
                                                if(removeFriend[1].equals(friendsList.get(aa))){//We check whether the user name given in the command is in the friend list.
                                                    friendsList.remove(aa);
                                                    System.out.println("-----------------------");
                                                    System.out.println("Command:\t"+line2);
                                                    System.out.println(removeFriend[1] + " has been successfully removed from your friend list.");
                                                    tt = true;//If it is in the friends list, the user name given in the command is 'tt' true and 'no such friend!'it keeps him from printing.
                                                    break;
                                                }
                                            }if (!tt){
                                                System.out.println("-----------------------");
                                                System.out.println("Command:\t"+line2);
                                                System.out.println("No such friend!");
                                            }ss = true;
                                        }
                                    }if(!ss){
                                        System.out.println("-----------------------");
                                        System.out.println("Command:\t"+line2);
                                        System.out.println("Error: Please sign in and try again.");
                                    }
                                }




                            }
                            else if (line2.contains("ADDPOST-TEXT")) {//We use this code to add text post.
                                String[] textPost = line2.split("\t");
                                Date rightNow = new Date();
                                if(line_count > commandCount){//We're looking at whether the command was given to us before or after signing in.
                                    continue;
                                }else {
                                    ArrayList<String> nothing = new ArrayList<String>();//When adding a new post, we add an empty list inside the 'posts' list.
                                    posts.add(nothing);
                                    posts post = new posts();//Thanks to the Posts class, we add posts given in the command to the 'posts' list.
                                    if(textPost.length == 5){//We check for tagged userName.
                                        System.out.println("-----------------------");
                                        System.out.println("Command:\t"+line2);
                                        post.typePost = textPost[1];
                                        post.longitude = textPost[2];
                                        post.latitude = textPost[3];
                                        post.tagFriends = textPost[4];
                                        posts.get(postCounter).add(post.typePost);
                                        posts.get(postCounter).add("Date:\t"+ rightNow.toString());
                                        posts.get(postCounter).add("Location:\t"+post.longitude+ ", "+post.latitude);
                                        posts.get(postCounter).add("Friends tagged in this post:\t");
                                        String[] tagged = post.tagFriends.split(":");
                                        for(int tag = 0;tag < tagged.length;tag++){
                                            boolean tagging = false;
                                            for(int qq=0;qq<friendsList.size();qq++){
                                                if (tagged[tag].equals(friendsList.get(qq))){//We're checking to see if the tagged username is in the friends list.
                                                    posts.get(postCounter).set(3,posts.get(postCounter).get(3) +"\t"+ tagged[tag]);
                                                    tagging = true;

                                                }
                                            }if (!tagging){
                                                System.out.println(tagged[tag]+" is not your friend, and will not be tagged!");
                                            }
                                        }System.out.println("The post has been successfully added.");
                                        if(posts.get(postCounter).get(3).equals("Friends tagged in this post:\t")){/*If the user names that you want to tag are not in the 'friends list',
                                                 we do this to avoid printing 'Friends tagged in this post:\t'.*/
                                            posts.get(postCounter).remove(3);
                                        }
                                        postCounter++;
                                    }else {
                                        post.typePost = textPost[1];
                                        post.longitude = textPost[2];
                                        post.latitude = textPost[3];
                                        System.out.println("-----------------------");
                                        System.out.println("Command:\t"+line2);
                                        System.out.println("The post has been successfully added.");
                                        posts.get(postCounter).add(post.typePost);
                                        posts.get(postCounter).add("Date:\t"+ rightNow.toString());
                                        posts.get(postCounter).add("Location:\t"+post.longitude+ ",\t"+post.latitude);
                                        postCounter++;
                                    }
                                }




                            }
                            else if (line2.contains("ADDPOST-IMAGE")) {//We use this code to add image post.
                                String[] imagePost = line2.split("\t");
                                Date rightNow = new Date();
                                if(line_count > commandCount){//We're looking at whether the command was given to us before or after signing in.
                                    continue;
                                }else {
                                    ArrayList<String> nothing = new ArrayList<String>();//When adding a new post, we add an empty list inside the 'posts' list.
                                    posts.add(nothing);
                                    posts post = new posts();//Thanks to the Posts class, we add posts given in the command to the 'posts' list.
                                    if(imagePost.length == 7){//We check for tagged userName.
                                        System.out.println("-----------------------");
                                        System.out.println("Command:\t"+line2);
                                        post.typePost = imagePost[1];
                                        post.longitude = imagePost[2];
                                        post.latitude = imagePost[3];
                                        post.tagFriends = imagePost[4];
                                        post.filePath = imagePost[5];
                                        post.resolution = imagePost[6];
                                        posts.get(postCounter).add(post.typePost);
                                        posts.get(postCounter).add("Date:\t"+ rightNow.toString());
                                        posts.get(postCounter).add("Location:\t"+post.longitude+ ", "+post.latitude);
                                        posts.get(postCounter).add("Friends tagged in this post:\t");
                                        posts.get(postCounter).add("Image:\t"+post.filePath);
                                        posts.get(postCounter).add("Image resolution:\t"+post.resolution);
                                        String[] tagged = post.tagFriends.split(":");
                                        for(int tag = 0;tag < tagged.length;tag++){
                                            boolean tagging = false;
                                            for(int qq=0;qq<friendsList.size();qq++){
                                                if (tagged[tag].equals(friendsList.get(qq))){//We're checking to see if the tagged username is in the friends list.
                                                    posts.get(postCounter).set(3,posts.get(postCounter).get(3)+"\t"+ tagged[tag]);
                                                    tagging = true;
                                                }
                                            }if (!tagging){
                                                System.out.println(tagged[tag]+" is not your friend, and will not be tagged!");
                                            }
                                        }System.out.println("The post has been successfully added.");
                                        if(posts.get(postCounter).get(3).equals("Friends tagged in this post:\t")){/*If the user names that you want to tag are not in the 'friends list',
                                                 we do this to avoid printing 'Friends tagged in this post:\t'.*/
                                            posts.get(postCounter).remove(3);
                                        }
                                        postCounter++;
                                    }else {
                                        post.typePost = imagePost[1];
                                        post.longitude = imagePost[2];
                                        post.latitude = imagePost[3];
                                        post.filePath = imagePost[4];
                                        post.resolution = imagePost[5];
                                        System.out.println("-----------------------");
                                        System.out.println("Command:\t"+line2);
                                        System.out.println("The post has been successfully added.");
                                        posts.get(postCounter).add(post.typePost);
                                        posts.get(postCounter).add("Date:\t"+ rightNow.toString());
                                        posts.get(postCounter).add("Location:\t"+post.longitude+ ",\t"+post.latitude);
                                        posts.get(postCounter).add("Image:\t"+post.filePath);
                                        posts.get(postCounter).add("Image resolution:\t"+post.resolution);
                                        postCounter++;
                                    }
                                }




                            }
                            else if (line2.contains("ADDPOST-VIDEO")) {//We use this code to add video post.
                                String[] videoPost = line2.split("\t");
                                Date rightNow = new Date();
                                if(line_count > commandCount){//We're looking at whether the command was given to us before or after signing in.
                                    continue;
                                }else {
                                    ArrayList<String> nothing = new ArrayList<String>();//When adding a new post, we add an empty list inside the 'posts' list.
                                    posts.add(nothing);
                                    posts post = new posts();//Thanks to the Posts class, we add posts given in the command to the 'posts' list.
                                    if (videoPost.length == 7){//We check for tagged userName.
                                        System.out.println("-----------------------");
                                        System.out.println("Command:\t"+line2);
                                        post.typePost = videoPost[1];
                                        post.longitude = videoPost[2];
                                        post.latitude = videoPost[3];
                                        post.tagFriends = videoPost[4];
                                        post.filePath = videoPost[5];
                                        post.videoDuration = videoPost[6];
                                        posts.get(postCounter).add(post.typePost);
                                        posts.get(postCounter).add("Date:\t"+ rightNow.toString());
                                        posts.get(postCounter).add("Location:\t"+post.longitude+ ", "+post.latitude);
                                        posts.get(postCounter).add("Friends tagged in this post:\t");
                                        posts.get(postCounter).add("Video:\t"+post.filePath);
                                        posts.get(postCounter).add("Video duration:\t"+post.videoDuration);
                                        String[] tagged = post.tagFriends.split(":");
                                        for(int tag = 0;tag < tagged.length;tag++){
                                            boolean tagging = false;
                                            for(int qq=0;qq<friendsList.size();qq++){
                                                if (tagged[tag].equals(friendsList.get(qq))){//We're checking to see if the tagged username is in the friends list.
                                                    posts.get(postCounter).set(3,posts.get(postCounter).get(3)+"\t"+ tagged[tag]);
                                                    tagging = true;
                                                }
                                            }if (!tagging){
                                                System.out.println(tagged[tag]+" is not your friend, and will not be tagged!");
                                            }
                                        }System.out.println("The post has been successfully added.");
                                        if(posts.get(postCounter).get(3).equals("Friends tagged in this post:\t")){/*If the user names that you want to tag are not in the 'friends list',
                                                 we do this to avoid printing 'Friends tagged in this post:\t'.*/
                                            posts.get(postCounter).remove(3);
                                        }
                                        postCounter++;
                                    }else {
                                        post.typePost = videoPost[1];
                                        post.longitude = videoPost[2];
                                        post.latitude = videoPost[3];
                                        post.filePath = videoPost[4];
                                        post.videoDuration = videoPost[5];
                                        System.out.println("-----------------------");
                                        System.out.println("Command:\t"+line2);
                                        System.out.println("The post has been successfully added.");
                                        posts.get(postCounter).add(post.typePost);
                                        posts.get(postCounter).add("Date:\t"+ rightNow.toString());
                                        posts.get(postCounter).add("Location:\t"+post.longitude+ ",\t"+post.latitude);
                                        posts.get(postCounter).add("Video:\t"+post.filePath);
                                        posts.get(postCounter).add("Video duration:\t"+post.videoDuration);
                                        postCounter++;
                                    }
                                }



                            }
                            else if (line2.contains("REMOVELASTPOST")) {//We use this code to remove last post.
                                if(line_count > commandCount){//We're looking at whether the command was given to us before or after signing in.
                                    continue;
                                }else {
                                    if(posts.size() == 0){
                                        System.out.println("-----------------------");
                                        System.out.println("Command:\t"+line2);
                                        System.out.println("Error: You do not have any post.");
                                    }else {
                                        posts.remove(postCounter-1);
                                        postCounter--;
                                        System.out.println("-----------------------");
                                        System.out.println("Command:\t"+line2);
                                        System.out.println("Your last post has been successfully removed.");

                                    }
                                }




                            }
                            else if (line2.contains("BLOCK") && !line2.contains("UNBLOCK") && !line2.contains("SHOWBLOCKEDFRIENDS") && !line2.contains("SHOWBLOCKEDUSERS")) {//We use this code to block user.
                                String[] blockuser = line2.split("\t");
                                if(line_count > commandCount){//We're looking at whether the command was given to us before or after signing in.
                                    continue;
                                }else {
                                    boolean tt = false;
                                    for (int ab = 0; ab < usersList.size(); ab++) {
                                        if (blockuser[1].equals(usersList.get(ab).get(2))) {//We check whether the user name given in the command is in the 'usersList' list.
                                            blockusers.add(blockuser[1]);
                                            System.out.println("-----------------------");
                                            System.out.println("Command:\t" + line2);
                                            System.out.println(blockuser[1] + " has been successfully blocked.");
                                            tt = true;
                                            break;
                                        }
                                    }
                                    if (!tt) {
                                        System.out.println("-----------------------");
                                        System.out.println("Command:\t" + line2);
                                        System.out.println("No such user!");
                                    }
                                }




                            }
                            else if (line2.contains("UNBLOCK")) {//We use this code to unblock user.
                                String[] unblockuser = line2.split("\t");
                                if(line_count > commandCount){//We're looking at whether the command was given to us before or after signing in.
                                    continue;
                                } else {
                                    boolean tt = false;
                                    for (int ab = 0; ab < blockusers.size(); ab++) {
                                        if (unblockuser[1].equals(blockusers.get(ab))) {//We are looking at whether the user name that is requested to be unblocked is in the 'blockusers' list.
                                            blockusers.remove(unblockuser[1]);
                                            System.out.println("-----------------------");
                                            System.out.println("Command:\t" + line2);
                                            System.out.println(unblockuser[1] + " has been successfully unblocked.");
                                            tt = true;
                                            break;
                                        }
                                    }
                                    if (!tt) {
                                        boolean ss = false;
                                        for (int ab = 0; ab < usersList.size(); ab++) {
                                            if(usersList.get(ab).get(2).equals(unblockuser[1])){//We are looking at whether the user name that you want to unblock is in the 'usersList' list.
                                                System.out.println("-----------------------");
                                                System.out.println("Command:\t" + line2);
                                                System.out.println("No such user in your blocked-user list!");
                                                ss = true;
                                            }
                                        }if (!ss){
                                            System.out.println("-----------------------");
                                            System.out.println("Command:\t" + line2);
                                            System.out.println("No such user!");
                                        }
                                    }
                                }




                            }
                            else if (line2.contains("LISTFRIENDS")) {//We use this code to show the friends list.
                                if(line_count > commandCount){//We're looking at whether the command was given to us before or after signing in.
                                    continue;
                                } else {
                                    if(friendsList.size() == 0){
                                        System.out.println("-----------------------");
                                        System.out.println("Command:\t"+line2);
                                        System.out.println("You have not added any friend yet!");
                                    }else{
                                        System.out.println("-----------------------");
                                        System.out.println("Command:\t"+line2);
                                        for (int j = 0; j < friendsList.size(); j++){
                                            for (int ab = 0; ab < usersList.size(); ab++) {
                                                if(usersList.get(ab).get(2).equals(friendsList.get(j))){//We search and find every element in the' friendsList 'list in the' usersList ' list. Then we print your information.
                                                    System.out.println("Name: " + usersList.get(ab).get(1) + "\nUsername: "+usersList.get(ab).get(2) +
                                                            "\nDate of Birth: "+ usersList.get(ab).get(4) + "\nSchool: "+ usersList.get(ab).get(5));
                                                    System.out.println("-----------------------");
                                                }
                                            }
                                        }
                                    }
                                }



                            }
                            else if (line2.contains("LISTUSERS")) {//We use this code to show the users list.
                                if(line_count > commandCount){//We're looking at whether the command was given to us before or after signing in.
                                    continue;
                                } else{
                                    System.out.println("-----------------------");
                                    System.out.println("Command:\t"+line2);
                                    for (int ab = 0; ab < usersList.size(); ab++) {
                                        System.out.println("Name: " + usersList.get(ab).get(1) + "\nUsername: "+usersList.get(ab).get(2) +
                                                "\nDate of Birth: "+ usersList.get(ab).get(4) + "\nSchool: "+ usersList.get(ab).get(5));
                                        System.out.println("-----------------------");
                                    }
                                }




                            }
                            else if (line2.contains("SHOWBLOCKEDFRIENDS")) {//We use this code to show the block friends list.
                                if(line_count > commandCount){//We're looking at whether the command was given to us before or after signing in.
                                    continue;
                                } else{
                                    System.out.println("-----------------------");
                                    System.out.println("Command:\t"+line2);
                                    boolean t = false;
                                    for (int ab = 0; ab < blockusers.size(); ab++) {
                                        for (int bb = 0; bb < friendsList.size(); bb++) {
                                            if (friendsList.get(bb).equals(blockusers.get(ab))){//We check that users in the' blockusers 'list are in the' friendsList ' list and print their information.
                                                for (int j = 0; j < usersList.size(); j++) {
                                                    if (usersList.get(j).get(2).equals(blockusers.get(ab))){
                                                        System.out.println("Name: " + usersList.get(j).get(1) + "\nUsername: "+usersList.get(j).get(2) +
                                                                "\nDate of Birth: "+ usersList.get(j).get(4) + "\nSchool: "+ usersList.get(j).get(5));
                                                        System.out.println("-----------------------");
                                                    }

                                                }t = true;
                                            }
                                        }
                                    }if (!t){
                                        System.out.println("You haven't blocked any friend yet!");
                                    }
                                }





                            }
                            else if (line2.contains("SHOWBLOCKEDUSERS")) {//We use this code to show the block users list.
                                if(line_count > commandCount){//We're looking at whether the command was given to us before or after signing in.
                                    continue;
                                } else{
                                    System.out.println("-----------------------");
                                    System.out.println("Command:\t"+line2);
                                    boolean t = false;
                                    for (int ab = 0; ab < blockusers.size(); ab++) {
                                        for (int j = 0; j < usersList.size(); j++) {
                                            if (usersList.get(j).get(2).equals(blockusers.get(ab))){//We search for users in the' blockusers 'list in the' usersList ' list and print their information.
                                                System.out.println("Name: " + usersList.get(j).get(1) + "\nUsername: "+usersList.get(j).get(2) +
                                                        "\nDate of Birth: "+ usersList.get(j).get(4) + "\nSchool: "+ usersList.get(j).get(5));
                                                System.out.println("-----------------------");
                                                t = true;
                                            }
                                        }
                                    }if (!t){
                                        System.out.println("You haven't blocked any user yet!");
                                    }
                                }
                            }
                        }br.close();
                        break;
                    }
                }
                if(count == 0){//If the user name and password do not match any users, we come here.
                    System.out.println("-----------------------");
                    System.out.println("Command:\t"+line1);
                    System.out.println("Invalid username or password! Please try again.");
                }
            }
            else if (line_count > commandCount){//We're looking at whether the command was given to us before or after signing in.
                System.out.println("-----------------------");
                System.out.println("Command:\t"+line1);
                System.out.println("Error: Please sign in and try again.");
            }
        }
        br1.close();
    }
}