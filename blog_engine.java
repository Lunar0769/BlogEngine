import java.io.*;
import java.sql.*;
import java.util.*;

public class blog_engine{
    
    public static void main(String[] args) throws Exception {
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/blogengine", "root", "");
        HashMap <String,String> user_details = new HashMap<>();
        User User = new User();
        ArrayList<String>E_mail = new ArrayList<>();
        HashMap<String,String>title_hash = new HashMap<>();
        HashMap<String,String>author_hash = new HashMap<>();
        Scanner sc = new Scanner(System.in);
        String Active_user="";
        boolean active = false;
        String Adminid = "Kavya";
        String Admipass = "password";
        
        // Instance Block
        {
            String user_sql = "SELECT User_id, Password FROM user_details";
            PreparedStatement u_pst = con.prepareStatement(user_sql);
            ResultSet u_rs = u_pst.executeQuery();
            while (u_rs.next()) {
                User.add(u_rs.getString("User_id")); 
                user_details.put(u_rs.getString("User_id"), u_rs.getString("Password"));              
            }
            String email_sql = "SELECT Email FROM user_details";
            PreparedStatement e_pst = con.prepareStatement(email_sql);
            ResultSet e_rs = e_pst.executeQuery();
            while (e_rs.next()) {
                E_mail.add(e_rs.getString("Email"));                                   
            }
            String blog_sql = "SELECT Author,title FROM blogs";
            PreparedStatement b_pst = con.prepareStatement(blog_sql);
            ResultSet b_rs = b_pst.executeQuery();
            while (b_rs.next()) {
                title_hash.put(b_rs.getString("Author")+b_rs.getString("title"),b_rs.getString("title"));  
                author_hash.put(b_rs.getString("Author")+b_rs.getString("title"),b_rs.getString("Author"));                                 
            }
        }
        System.out.println((con!=null)?"Connection Successfull":" Connection failed");
        try {
            System.out.println();
            System.out.println("             Welcome to Blog Engine");
            System.out.println("      Create your imagination into words      ");
            System.out.println();
            
            boolean tf = true;
            while (tf) {
                
                System.out.println(" To exit program type exit.");
                System.out.print(" Are you a new user? ");
                String rep = sc.nextLine();
                rep = rep.toLowerCase();
                if(rep.equals("yes"))
                {
                    System.out.println();
                    System.out.println();
                    System.out.println("===== LOGIN =====");
                    System.out.println("Enter Your Firstname:");
                    String first_name = sc.nextLine().toLowerCase();
                    System.out.println("Enter Your Lastname:");
                    String last_name = sc.nextLine().toLowerCase();
                    String Email=null;
                    boolean email_tf = true;
                    while (email_tf) {
                        
                        System.out.println("Enter your Email");
                        Email = sc.nextLine().toLowerCase();
                        if(Email == null)
                        {
                            System.out.println("Email cannot be null");
                        }
                        if( Email.indexOf('@')>1 && Email.indexOf(".com")>0)
                        {
                            if(E_mail.contains(Email))
                            {
                                System.out.println("Email already exist");
                            }
                            else 
                            {
                                E_mail.add(Email);
                                email_tf = false;
                            }
                        }
                        else
                        {
                            System.out.println("Email formate is not valid.");
                        }
                    }
                    System.out.println();
                    boolean user_tf = true;
                    boolean pass_tf = true;
                    while (user_tf) {
                        System.out.println("Enter your user name");
                        String User_id = sc.nextLine();
                        if(User.contains(User_id))
                        {
                            System.out.println("User ID is already taken");
                        }
                        else
                        {
                            while (pass_tf) {
                                System.out.println("Enter passWord");
                                String pass = sc.nextLine();              
                                System.out.println("Confirm passWord");
                                String con_pass = sc.nextLine();
                                if(pass.equals(con_pass))
                                {
                                    System.out.println("Password confirmed");
                                    User.add(User_id);
                                    user_details.put(User_id,pass);
                                    String insert_user = "INSERT INTO user_details VALUES ('"+User_id+"', '"+first_name+"', '"+last_name+"', '"+Email+"', '"+pass+"')";
                                    PreparedStatement pst = con.prepareStatement(insert_user);
                                    String updatelog = "Call UpdateLog(?,?)";
                                    PreparedStatement pst2 = con.prepareStatement(updatelog);
                                    pst2.setString(1,User_id);
                                    pst2.setString(2,"Login");
                                    int u = pst2.executeUpdate();
                                    int r = pst.executeUpdate();
                                    if(r>0&&u>0)
                                    {
                                        Thread.sleep(1200);   
                                        System.out.println("Login Successfull");
                                        
                                        Active_user = Active_user+User_id;
                                        active = true;
                                    }
                                    else
                                    {
                                        System.out.println("Some problem ocured.");
                                    }
                                    pass_tf = false;
                                    user_tf = false;
                                }
                                else
                                {
                                    System.out.println("Password don't match.");
                                    Thread.sleep(1000);
                                }              
                            }
                        }
                    }
                    System.out.println();
                }
                else if(rep.equals("no"))
                {       
                    System.out.println();
                    System.out.println();
                    System.out.println("===== SIGNUP =====");
                    System.out.println();
                    boolean sign_tf = true;
                    while (sign_tf) {
                        
                        System.out.println("Enter User id");
                        String id = sc.nextLine();
                        System.out.println("Enter Password");
                        String pass = sc.nextLine();
                        if(id.equals(Adminid) && pass.equals(Admipass))
                        {
                            Active_user = "admin";
                            active = true;
                            sign_tf = false;
                        }
                        else if(User.contains(id))
                        {
                            if(pass.equals(user_details.get(id)))
                            {
                                Thread.sleep(2000);
                                String updatelog = "Call UpdateLog(?,?)";
                                PreparedStatement pst2 = con.prepareStatement(updatelog);
                                pst2.setString(1,id);
                                pst2.setString(2,"Signup");
                                int u = pst2.executeUpdate();
                                if(u>0)
                                {
                                    System.out.println("Sign in Successfull");
                                    System.out.println();
                                    System.out.println("Welcome "+id);
                                    Active_user = Active_user+id;
                                    active = true;
                                    sign_tf = false;
                                    Thread.sleep(1500);
                                }
                            }
                            else
                            {
                                System.out.println("User ID or Password is incorrect");
                            }
                        }
                        else
                        {
                            System.out.println("User ID or Password is incorrect");
                        }
                    }
                }
                else if(rep.equals("exit"))
                {
                    
                    System.out.println("Thank you for using Blog Engine.");
                    tf = false;
                }
                else
                {
                    System.out.println("please Choose a valid option");
                }
                if(active)
                {
                    boolean functiontf = true;
                    while (functiontf) {
                        System.out.println();
                        System.out.println();
                        if(Active_user.equals("admin")) 
                        {
                            System.out.println("press");
                            System.out.println("1. Search Blog by Title");
                            System.out.println("2. Search Blog by Author name");
                            System.out.println("3. Search Blog by any word");
                            System.out.println("4. Check Reports");
                            System.out.println("5. Delete User");
                            System.out.println("6. Delete Blog");
                            System.out.println("7. log out");
                            System.out.println("8. Settings");
                            System.out.println("9. Exit");
                        }
                    else
                    {
                            System.out.println("press");
                            System.out.println("1. Write Blog");
                            System.out.println("2. Search Blog by Title");
                            System.out.println("3. Search Blog by Author name");
                            System.out.println("4. Search Blog by any word");
                            System.out.println("5. Delete Blog");
                            System.out.println("6. Report Blog");
                            System.out.println("7. log out");
                            System.out.println("8. Settings");
                            System.out.println("9. Exit");
                        }
                        int ch = sc.nextInt();
                        if(Active_user.equals("admin"))
                        {
                            switch (ch) {
                                
                            case 1:
                            {
                                sc.nextLine();
                                System.out.println("Enter title to find blog");
                                String title = sc.nextLine();
                                if(title_hash.containsValue(title))
                                {
                                    System.out.println();
                                    String get_blog = "SELECT * FROM blogs WHERE title = '"+title+"'";
                                    PreparedStatement get_pst = con.prepareStatement(get_blog);
                                    ResultSet get_rs = get_pst.executeQuery();
                                    while (get_rs.next()) {
                                        System.out.println("Title : "+get_rs.getString("title")+"\nWritten by : "+get_rs.getString("Author"));
                                        System.out.print("Blog : ");
                                        Clob c = get_rs.getClob("blog");
                                        Reader r = c.getCharacterStream();
                                        int i = r.read();
                                        while(i!=-1)
                                        {
                                            System.out.print((char)i);
                                            i=r.read();
                                        }
                                    }
                                }
                                else
                                {
                                    System.out.println("Blog not found.");
                                }
                                break;
                            }
                            case 2:
                            {
                                sc.nextLine();
                                System.out.println("Enter author name to find blog");
                                String name = sc.nextLine();
                                if(author_hash.containsValue(name))
                                {
                                    String get_blog = "SELECT * FROM blogs WHERE Author = '"+name+"'";
                                    PreparedStatement get_pst = con.prepareStatement(get_blog);
                                    ResultSet get_rs = get_pst.executeQuery();
                                    while (get_rs.next()) {
                                        System.out.println();
                                        System.out.println("Title : "+get_rs.getString("title")+"\nWritten by : "+get_rs.getString("Author"));
                                        System.out.print("Blog :");
                                        Clob c = get_rs.getClob("blog");
                                        Reader r = c.getCharacterStream();
                                        int i = r.read();
                                        while(i!=-1)
                                        {
                                            System.out.print((char)i);
                                            i=r.read();
                                        }
                                        System.out.println();
                                    }
                                }
                                else
                                {
                                    System.out.println("Blog not found.");
                                }
                                break;
                            }
                            case 3:
                            {
                                sc.nextLine();
                                boolean found = false;
                                System.out.println("Enter word to find blog that reflects it");
                                String word = sc.nextLine();
                                String get_blogs = "SELECT * FROM blogs";
                                PreparedStatement get_pst = con.prepareStatement(get_blogs);
                                ResultSet get_rs = get_pst.executeQuery();
                                String[] arr; 
                                System.out.println();
                                while (get_rs.next()) {
                                    String line = ""; 
                                    Clob c = get_rs.getClob("blog");
                                    Reader r = c.getCharacterStream();
                                    int i = r.read();
                                    while(i!=-1)
                                    {
                                        line = line + (char)i;
                                        i=r.read();
                                    }
                                    arr = line.split(" ");
                                    for(int j=0;j<arr.length;j++)
                                    {
                                        if(arr[j].equals(word))
                                        {
                                            System.out.println("Title : "+get_rs.getString("title")+"\nWritten by : "+get_rs.getString("Author"));
                                            System.out.println(line);
                                            found = true;
                                        }
                                    }
                                }
                                if(!found)
                                {
                                    System.out.println("There is no blog that contains '"+word+"' word in it");
                                }
                                break;
                            }
                            case 4: 
                            {
                                System.out.println("Checking reports...");
                                String report = "Select *from reported_blogs";
                                PreparedStatement get_pst = con.prepareStatement(report);
                                ResultSet get_rs = get_pst.executeQuery();
                                System.out.println();
                                while (get_rs.next()) {
                                    System.out.print(get_rs.getInt(1)+" "+get_rs.getString(2)+" "+get_rs.getString(3));
                                    System.out.print(" "+get_rs.getString(4)+" "+get_rs.getTime(5));
                                }
                                boolean rep_tf = true;
                                while(rep_tf)
                                {
                                    System.out.println("press");
                                    System.out.println("1. Delete report id");
                                    System.out.println("2. Delete all reports");
                                    System.out.println("3. Exit");
                                    int rch = sc.nextInt();
                                    switch(rch)
                                    {
                                        case 1:
                                        {
                                            System.out.println("Enter report id");
                                            int r = sc.nextInt();
                                            String deleteReport = "DELETE FROM reported_blogs WHERE report_id = " + r;
                                            PreparedStatement deletePst = con.prepareStatement(deleteReport);
                                            int deleteRs = deletePst.executeUpdate();
                                            if(deleteRs > 0)
                                            {
                                                System.out.println("Report deleted successfully");
                                            }
                                            else
                                            {
                                                System.out.println("Error deleting report");
                                            }
                                            break;
                                        }
                                        case 2:
                                        {
                                            String deleteAllReports = "DELETE FROM reported_blogs";
                                            PreparedStatement deleteAllPst = con.prepareStatement(deleteAllReports);
                                            int deleteAllRs = deleteAllPst.executeUpdate();
                                            if(deleteAllRs > 0)
                                            {
                                                System.out.println("All reports deleted successfully");
                                            }
                                            else
                                            {
                                                System.out.println("Error deleting all reports");
                                            }
                                            break;
                                        }
                                        case 3:
                                        {
                                            rep_tf = false;
                                            break;
                                        }
                                        default:
                                        {
                                            System.out.println("Please select a valid option");
                                            break;
                                        }
                                        
                                    }
                                }
                                break;
                            }
                            case 5:
                            {
                                System.out.println("Enter user ID to delete:");
                                String userIdToDelete = sc.next();
                                if(User.contains(userIdToDelete))
                                {
                                    String deleteUser = "DELETE FROM user_details WHERE User_id = '" + userIdToDelete + "'";
                                    PreparedStatement deletePst = con.prepareStatement(deleteUser);
                                    int deleteRs = deletePst.executeUpdate();
                                    if(deleteRs > 0)
                                    {
                                        User.remove(userIdToDelete);
                                        E_mail.remove(userIdToDelete);
                                        user_details.remove(userIdToDelete);
                                        System.out.println("User deleted successfully");
                                    }
                                    else
                                    {
                                        System.out.println("Error deleting user");
                                    }
                                }
                                else
                                {
                                    System.out.println("User not found");
                                }
                                break;
                            }
                            case 6:
                            {
                                System.out.println("Enter blog title to delete:");
                                String blogTitleToDelete = sc.next();
                                if(title_hash.containsValue(blogTitleToDelete))
                                {
                                    String deleteBlog = "DELETE FROM blogs WHERE title = '" + blogTitleToDelete + "'";
                                    PreparedStatement deletePst = con.prepareStatement(deleteBlog);
                                    int deleteRs = deletePst.executeUpdate();
                                    if(deleteRs > 0)
                                    {
                                        title_hash.remove(blogTitleToDelete);
                                        author_hash.remove(blogTitleToDelete);
                                        System.out.println("Blog deleted successfully");
                                    }
                                    else
                                    {
                                        System.out.println("Error deleting blog");
                                    }
                                }
                                else
                                {
                                    System.out.println("Blog not found");
                                }
                                break;
                            }
                            case 7:
                            {
                                System.out.println("you are now logging off.");
                                Thread.sleep(3000);
                                functiontf = false;
                                active = false;
                                Active_user="";
                                sc.nextLine();
                                break;
                                
                            }
                            case 8:
                            {
                                boolean ch_7 = true;
                                while (ch_7) {
                                    System.out.println();
                                    System.out.println("press");
                                    System.out.println("1. Change User id");
                                    System.out.println("2. Change User Name");
                                    System.out.println("3. Change Password");
                                    System.out.println("4. Change Email");
                                    System.out.println("5. Exit");
                                    int op = sc.nextInt();
                                    switch (op) {
                                        case 1:
                                        {
                                            System.out.println("Enter new User ID:");
                                            sc.nextLine();
                                            String newUserId = sc.nextLine();
                                            if (User.contains(newUserId)) {
                                                System.out.println("User ID already exists. Please choose a different one.");
                                            } else {
                                                String updateUserId = "UPDATE user_details SET User_id = '" + newUserId + "' WHERE User_id = '" + Active_user + "'";
                                                PreparedStatement updatePst = con.prepareStatement(updateUserId);
                                                int updateRs = updatePst.executeUpdate();
                                                if (updateRs > 0) {
                                                    User.remove(Active_user);
                                                    User.add(newUserId);
                                                    user_details.remove(Active_user);
                                                    user_details.put(newUserId, user_details.get(Active_user));
                                                    Active_user = newUserId;
                                                    System.out.println("User ID updated successfully.");
                                                } else {
                                                    System.out.println("Error updating User ID.");
                                                }
                                            }
                                            break;
                                        }
                                        case 2:
                                        {
                                            System.out.println("Enter new User Name:");
                                            sc.nextLine();
                                            String newUserName = sc.nextLine();
                                            if (User.contains(newUserName)) {
                                                System.out.println("User Name already exists. Please choose a different one.");
                                            } else {
                                                String updateUserName = "UPDATE user_details SET User_id = '" + newUserName + "' WHERE User_id = '" + Active_user + "'";
                                                PreparedStatement updatePst = con.prepareStatement(updateUserName);
                                                int updateRs = updatePst.executeUpdate();
                                                if (updateRs > 0) {
                                                    User.remove(Active_user);
                                                    User.add(newUserName);
                                                    user_details.remove(Active_user);
                                                    user_details.put(newUserName, user_details.get(Active_user));
                                                    Active_user = newUserName;
                                                    System.out.println("User Name updated successfully.");
                                                } else {
                                                    System.out.println("Error updating User Name.");
                                                }
                                            }
                                            break;
                                        }
                                        case 3:
                                        {
                                            System.out.println("Enter current password:");
                                            sc.nextLine();
                                            String currentPass = sc.nextLine();
                                            if (currentPass.equals(user_details.get(Active_user))) {
                                                System.out.println("Enter new password:");
                                                String newPass = sc.nextLine();
                                                System.out.println("Confirm new password:");
                                                String confirmPass = sc.nextLine();
                                                if (newPass.equals(confirmPass)) {
                                                    String updatePass = "UPDATE user_details SET Password = '" + newPass + "' WHERE User_id = '" + Active_user + "'";
                                                    PreparedStatement updatePst = con.prepareStatement(updatePass);
                                                    int updateRs = updatePst.executeUpdate();
                                                    if (updateRs > 0) {
                                                        user_details.put(Active_user, newPass);
                                                        System.out.println("Password updated successfully.");
                                                    } else {
                                                        System.out.println("Error updating password.");
                                                    }
                                                } else {
                                                    System.out.println("Passwords do not match.");
                                                }
                                            } else {
                                                System.out.println("Current password is incorrect.");
                                            }
                                            break;
                                        }
                                        case 4:
                                        {
                                            System.out.println("Enter new email:");
                                            sc.nextLine();
                                            String newEmail = sc.nextLine();
                                            if (E_mail.contains(newEmail)) {
                                                System.out.println("Email already exists. Please choose a different one.");
                                            } else {
                                                String updateEmail = "UPDATE user_details SET Email = '" + newEmail + "' WHERE User_id = '" + Active_user + "'";
                                                PreparedStatement updatePst = con.prepareStatement(updateEmail);
                                                int updateRs = updatePst.executeUpdate();
                                                if (updateRs > 0) {
                                                    E_mail.remove(Active_user);
                                                    E_mail.add(newEmail);
                                                    System.out.println("Email updated successfully.");
                                                } else {
                                                    System.out.println("Error updating email.");
                                                }
                                            }
                                            break;
                                        }
                                        case 5:
                                        {
                                            ch_7 = false;
                                            break;
                                        }
                                        default:
                                        {
                                            System.out.println("Please select valid option.");
                                            break;
                                        }
                                    }
                                }
                                break;
                            }  
                            case 9:
                            {
                                System.out.println("you are now logging off.");
                                Thread.sleep(2000);
                                functiontf = false;
                                System.out.println("you are now signing out.");
                                Thread.sleep(2000);
                                tf = false;
                                break;
                            }
                           
                            
                            
                            default:
                            {
                                System.out.println("Plaease choose a valid operation");
                                break;
                            }
                        }
                    }


                // USer system
                    else
                    {
                        switch (ch) {
                            case 1:
                            {
                                String title="";
                                boolean title_tf = true;
                                while (title_tf) {
                                    System.out.println("Enter title");
                                    sc.nextLine();
                                    title = sc.nextLine();
                                    if(title_hash.containsKey(Active_user+title))
                                    {
                                        System.out.println("title already taken");
                                    }
                                    else
                                    {
                                        title_tf = false;
                                    }
                                }
                                String path = "C://Users/Kavya/Desktop/Blogs";
                                File new_folder = new File(path,Active_user); 
                                if(!new_folder.exists())
                                {
                                    new_folder.mkdir();
                                }
                                File newBlog = new File(path+"/"+Active_user,title+".txt");
                                newBlog.createNewFile();
                                FileWriter new_blog = new FileWriter(newBlog,true);
                                boolean bolg_tf = true;
                                while(bolg_tf)
                                {
                                    System.out.println("Enter /submit to submit the blog");
                                    System.out.print("Write here ==> ");
                                    String line = sc.nextLine();
                                    if(line.toLowerCase().equals("/submit"))
                                    {
                                        bolg_tf = false;
                                    } 
                                    else
                                    {
                                    new_blog.append(line+"\n");
                                }
                                new_blog.flush();
                            }
                            FileReader fr = new FileReader(newBlog);
                            String setblog = "INSERT INTO blogs VALUES(?,?,?)";
                            PreparedStatement set_pst = con.prepareStatement(setblog);
                            set_pst.setString(1,Active_user); 
                            set_pst.setString(2,title); 
                            set_pst.setCharacterStream(3,fr);
                            int r = set_pst.executeUpdate(); 
                            System.out.println((r>0)?"Blog uploaded successfully":"Blog upload failed");
                            if(r>0)
                            {
                                author_hash.put(Active_user+title,Active_user);
                                title_hash.put(Active_user+title,title);
                            }
                            new_blog.close();
                            break;
                        }
                        case 2:
                        {
                            sc.nextLine();
                            System.out.println("Enter title to find blog");
                            String title = sc.nextLine();
                            if(title_hash.containsValue(title))
                            {
                                System.out.println();
                                String get_blog = "SELECT * FROM blogs WHERE title = '"+title+"'";
                                PreparedStatement get_pst = con.prepareStatement(get_blog);
                                ResultSet get_rs = get_pst.executeQuery();
                                while (get_rs.next()) {
                                    System.out.println("Title : "+get_rs.getString("title")+"\nWritten by : "+get_rs.getString("Author"));
                                    System.out.print("Blog : ");
                                    Clob c = get_rs.getClob("blog");
                                    Reader r = c.getCharacterStream();
                                    int i = r.read();
                                    while(i!=-1)
                                    {
                                        System.out.print((char)i);
                                        i=r.read();
                                    }
                                }
                            }
                            else
                            {
                                System.out.println("Blog not found.");
                            }
                            break;
                        }
                        case 3:
                        {
                            sc.nextLine();
                            System.out.println("Enter author name to find blog");
                            String name = sc.nextLine();
                            if(author_hash.containsValue(name))
                            {
                                String get_blog = "SELECT * FROM blogs WHERE Author = '"+name+"'";
                                PreparedStatement get_pst = con.prepareStatement(get_blog);
                                ResultSet get_rs = get_pst.executeQuery();
                                while (get_rs.next()) {
                                    System.out.println();
                                    System.out.println("Title : "+get_rs.getString("title")+"\nWritten by : "+get_rs.getString("Author"));
                                    System.out.print("Blog :");
                                    Clob c = get_rs.getClob("blog");
                                    Reader r = c.getCharacterStream();
                                    int i = r.read();
                                    while(i!=-1)
                                    {
                                        System.out.print((char)i);
                                        i=r.read();
                                    }
                                    System.out.println();
                                }
                            }
                            else
                            {
                                System.out.println("Blog not found.");
                            }
                            break;
                        }
                        case 4:
                        {
                            sc.nextLine();
                            boolean found = false;
                            System.out.println("Enter word to find blog that reflects it");
                            String word = sc.nextLine();
                            String get_blogs = "SELECT * FROM blogs";
                            PreparedStatement get_pst = con.prepareStatement(get_blogs);
                            ResultSet get_rs = get_pst.executeQuery();
                            String[] arr; 
                            System.out.println();
                            while (get_rs.next()) {
                                String line = ""; 
                                Clob c = get_rs.getClob("blog");
                                Reader r = c.getCharacterStream();
                                int i = r.read();
                                while(i!=-1)
                                {
                                    line = line + (char)i;
                                    i=r.read();
                                }
                                arr = line.split(" ");
                                for(int j=0;j<arr.length;j++)
                                {
                                    if(arr[j].equals(word))
                                    {
                                        System.out.println("Title : "+get_rs.getString("title")+"\nWritten by : "+get_rs.getString("Author"));
                                        System.out.println(line);
                                        found = true;
                                    }
                                }
                            }
                            if(!found)
                            {
                                System.out.println("There is no blog that contains '"+word+"' word in it");
                            }
                            break;
                        }
                        case 5:
                        {
                            con.setAutoCommit(false);
                            sc.nextLine();
                            System.out.println("Enter title to delete blog");
                            String title = sc.nextLine();
                            if(title_hash.containsValue(title))
                            {
                                
                                System.out.println();
                                String get_blog = "DELETE FROM blogs WHERE Author = '"+Active_user+"' and title = '"+title+"'";
                                PreparedStatement get_pst = con.prepareStatement(get_blog);
                                int get_rs = get_pst.executeUpdate();
                                if(get_rs>0)
                                {
                                    boolean ch_tf = true;
                                    while (ch_tf) {
                                        
                                        System.out.println("Are you sure you want to delete blog");
                                        String cho = sc.nextLine().toLowerCase();
                                        if(cho.equals("yes"))
                                        {
                                            con.commit();
                                            author_hash.remove(Active_user+title);
                                            title_hash.remove(Active_user+title);
                                            System.out.println("Blog deleted Successfull.");
                                            ch_tf = false;
                                        }
                                        else if(cho.equals("no"))
                                        {
                                            con.rollback();
                                            ch_tf = false;
                                        }
                                        else 
                                        {
                                            System.out.println("please give a valid answer");
                                        }
                                    }
                                }
                                else
                                {
                                    System.out.println("You are not Author of this blog. This action is invalid for you.");
                                }
                                
                            }
                            else
                            {
                                System.out.println("Blog not found.");
                            }
                            break;
                        }
                        case 6:
                        {
                            System.out.println("Enter title of the blog to report:");
                            sc.nextLine();
                            String title = sc.nextLine();
                            System.out.println("Enter author name of the blog to report:");
                            String author = sc.nextLine();
                            if(title_hash.containsValue(title) && author_hash.containsValue(author))
                            {
                                String report_blog = "Call report_blog(?,?,?)";
                                PreparedStatement report_pst = con.prepareStatement(report_blog);
                                report_pst.setString(1, Active_user);
                                report_pst.setString(2, author);
                                report_pst.setString(3, title);
                                int report_rs = report_pst.executeUpdate();
                                if(report_rs > 0)
                                {
                                    System.out.println("Blog reported successfully.");
                                }
                                else
                                {
                                    System.out.println("Error reporting blog.");
                                }
                            }
                            else
                            {
                                System.out.println("Blog not found.");
                            }
                            break;
                        }
                        case 7:
                        {
                            System.out.println("you are now logging off.");
                            Thread.sleep(3000);
                            functiontf = false;
                            active = false;
                            Active_user="";
                            sc.nextLine();
                            break;
                            
                        }
                        case 8:
                        {
                            boolean ch_7 = true;
                            while (ch_7) {
                                System.out.println();
                                System.out.println("press");
                                System.out.println("1. Change User id");
                                System.out.println("2. Change User Name");
                                System.out.println("3. Change Password");
                                System.out.println("4. Change Email");
                                System.out.println("5. Delete account");
                                System.out.println("6. Exit");
                                int op = sc.nextInt();
                                switch (op) {
                                    case 1:
                                    {
                                        System.out.println("Enter new User ID:");
                                        sc.nextLine();
                                        String newUserId = sc.nextLine();
                                        if (User.contains(newUserId)) {
                                            System.out.println("User ID already exists. Please choose a different one.");
                                        } else {
                                            String updateUserId = "UPDATE user_details SET User_id = '" + newUserId + "' WHERE User_id = '" + Active_user + "'";
                                            PreparedStatement updatePst = con.prepareStatement(updateUserId);
                                            int updateRs = updatePst.executeUpdate();
                                            if (updateRs > 0) {
                                                User.remove(Active_user);
                                                User.add(newUserId);
                                                user_details.remove(Active_user);
                                                user_details.put(newUserId, user_details.get(Active_user));
                                                Active_user = newUserId;
                                                System.out.println("User ID updated successfully.");
                                            } else {
                                                System.out.println("Error updating User ID.");
                                            }
                                        }
                                        break;
                                    }
                                    case 2:
                                    {
                                        System.out.println("Enter new User Name:");
                                        sc.nextLine();
                                        String newUserName = sc.nextLine();
                                        if (User.contains(newUserName)) {
                                            System.out.println("User Name already exists. Please choose a different one.");
                                        } else {
                                            String updateUserName = "UPDATE user_details SET User_id = '" + newUserName + "' WHERE User_id = '" + Active_user + "'";
                                            PreparedStatement updatePst = con.prepareStatement(updateUserName);
                                            int updateRs = updatePst.executeUpdate();
                                            if (updateRs > 0) {
                                                User.remove(Active_user);
                                                User.add(newUserName);
                                                user_details.remove(Active_user);
                                                user_details.put(newUserName, user_details.get(Active_user));
                                                Active_user = newUserName;
                                                System.out.println("User Name updated successfully.");
                                            } else {
                                                System.out.println("Error updating User Name.");
                                            }
                                        }
                                        break;
                                    }
                                    case 3:
                                    {
                                        System.out.println("Enter current password:");
                                        sc.nextLine();
                                        String currentPass = sc.nextLine();
                                        if (currentPass.equals(user_details.get(Active_user))) {
                                            System.out.println("Enter new password:");
                                            String newPass = sc.nextLine();
                                            System.out.println("Confirm new password:");
                                            String confirmPass = sc.nextLine();
                                            if (newPass.equals(confirmPass)) {
                                                String updatePass = "UPDATE user_details SET Password = '" + newPass + "' WHERE User_id = '" + Active_user + "'";
                                                PreparedStatement updatePst = con.prepareStatement(updatePass);
                                                int updateRs = updatePst.executeUpdate();
                                                if (updateRs > 0) {
                                                    user_details.put(Active_user, newPass);
                                                    System.out.println("Password updated successfully.");
                                                } else {
                                                    System.out.println("Error updating password.");
                                                }
                                            } else {
                                                System.out.println("Passwords do not match.");
                                            }
                                        } else {
                                            System.out.println("Current password is incorrect.");
                                        }
                                        break;
                                    }
                                    case 4:
                                    {
                                        System.out.println("Enter new email:");
                                        sc.nextLine();
                                        String newEmail = sc.nextLine();
                                        if (E_mail.contains(newEmail)) {
                                            System.out.println("Email already exists. Please choose a different one.");
                                        } else {
                                            String updateEmail = "UPDATE user_details SET Email = '" + newEmail + "' WHERE User_id = '" + Active_user + "'";
                                            PreparedStatement updatePst = con.prepareStatement(updateEmail);
                                            int updateRs = updatePst.executeUpdate();
                                            if (updateRs > 0) {
                                                E_mail.remove(Active_user);
                                                E_mail.add(newEmail);
                                                System.out.println("Email updated successfully.");
                                            } else {
                                                System.out.println("Error updating email.");
                                            }
                                        }
                                        break;
                                    }
                                    case 5:
                                    {
                                        System.out.println("Are you sure you want to delete your account?");
                                        sc.nextLine();
                                        String confirm = sc.nextLine().toLowerCase();
                                        if (confirm.equals("yes")) {
                                            String deleteAccount = "DELETE FROM user_details WHERE User_id = '" + Active_user + "'";
                                            PreparedStatement deletePst = con.prepareStatement(deleteAccount);
                                            int deleteRs = deletePst.executeUpdate();
                                            if (deleteRs > 0) {
                                                User.remove(Active_user);
                                                E_mail.remove(Active_user);
                                                user_details.remove(Active_user);
                                                System.out.println("Account deleted successfully.");
                                                Active_user = "";
                                                active = false;
                                            } else {
                                                System.out.println("Error deleting account.");
                                            }
                                        } else {
                                            System.out.println("Account deletion cancelled.");
                                        }
                                        break;
                                    }   
                                    case 6:
                                    {
                                        ch_7 = false;
                                        break;
                                    }
                                    default:
                                    {
                                        System.out.println("Please select valid option.");
                                        break;
                                    }
                                }
                            }
                            break;
                        }  
                        case 9:
                        {
                            System.out.println("you are now logging off.");
                            Thread.sleep(2000);
                            functiontf = false;
                            System.out.println("you are now signing out.");
                            Thread.sleep(2000);
                            tf = false;
                            break;
                        }
                        
                        default:
                        {
                            System.out.println("Plaease choose a valid operation");
                            break;
                        }
                    }
                    }
                }
                }
                }
            }
            
            catch (SQLException e)
            {
                System.out.println("SQL syntax error.");
                System.out.println(e);
            }
            
            catch (Exception e) {
                System.out.println("server is offline please try again later.");
                System.out.println(e);
            }
            finally
            {
                System.out.println("Thanks for using Blog Engine.");
            }
            
            sc.close();
        }
    }
    class User {
        private String userId;
        private Node head; // head of the linked list
        
        // public User(String userId) {
            //     this.userId = userId;
            //     this.head = null;
            // }
            
            private class Node {
                String nextUserId;
                Node next;
                
                public Node(String nextUserId) {
                    this.nextUserId = nextUserId;
                    this.next = null;
                }
            }
            
    public void add(String nextUserId) {
        Node newNode = new Node(nextUserId);
        if (head == null) {
            head = newNode;
        } else {
            Node current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
    }
    
    public void remove(String userId) {
        if (head == null) return;
        if (head.nextUserId.equals(userId)) {
            head = head.next;
            return;
        }
        Node current = head;
        while (current.next != null) {
            if (current.next.nextUserId.equals(userId)) {
                current.next = current.next.next;
                return;
            }
            current = current.next;
        }
    }
    
    public boolean contains(String is_user) {
        Node current = head;
        while (current != null) {
            if(current.nextUserId.equals(is_user))
            {
                return true;
            }
            current = current.next;
        }
        return false;
    }
}