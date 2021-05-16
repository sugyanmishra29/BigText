# BigText
Download the above BigText.apk file from this link: https://drive.google.com/file/d/1yoDyJvDyqbQsu0e9lAvOzOUHv27M1zWv/view?usp=sharing

## Key Features
1. Text is displayed like a threaded conversation. Latest entered text gets shown at the bottom of the conversation.
2. When the user closes and reopens the app, previous data is shown. This showing of previous data is handled in the background (as an Asynchronous task) so as to avoid any UI lag while using the app.
3. Empty messages cannot be sent. The app prompts the user to provide a text input.
4. Increment in text-size occurs upon long-pressing of the Display button, but this feature is buggy and needs to be fixed. 
5. User gets a VIBRATION HAPTIC FEEDBACK when the latest entered text appears in the conversation.

## Known Bugs (yet to be fixed)
1. If the user press and hold the display button, then entered text IN THE CURRENT MESSAGE should keep on increasing in size. However due to slow storing and fetching in SQLite database, the entered text IN THE SUBSEQUENT MESSAGE gets increased.
2. Text-sizes and increments in them have been hardcoded. 
3. Other minor bugs (unnoticed)

## Features yet to be implemented
1. Increment in text size needs to be shown using animations like Bigmoji feature. For the time being, vibration feedback has been implemented which gets triggered when there is an increment in text-size.
2. Further refinements in UI
