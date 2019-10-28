# App Description
  This app is a simple webview app with FCM notification + drawer submenus
  Pretty easy to customize and Run 
  Php - Firebase Notifications 
  Android - Webview 
  Pull To refresh
  Loader Options 
  String only customization  
  
# Set up FCM - Just add Configs :)
If you want the FCM for send push notifications to your customer, you must to follow this steps: Like a google maps setup, you must to create or edit your project on Google Api Console.
Go to this link: https://firebase.google.com/ and follow the instructions.
Android side
Edit res -> values -> strings.xml
• Set your register.php url on the server_url value (install PUSHPANEL BEFORE) • Put your Project ID on: google_api_sender_id
Web Server side
1. Put the push_panel folder on your host server (with php support)
2. Create a database and import the .sql database
3. Edit register.php and sendpush.php, change the mysql connection with your access.
4. Configure your Api KEY (from Google Api Console -> Credentials) on sendpush.php.
