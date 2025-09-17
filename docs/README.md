# Yoyo User Guide

![Yoyo Logo](https://via.placeholder.com/150x50/007acc/ffffff?text=YOYO)

> **Yoyo** is your friendly task management chatbot that helps you organize your tasks, deadlines, and events with ease!

## Table of Contents
- [Quick Start](#quick-start)
- [Features](#features)
- [Command Reference](#command-reference)
- [Examples](#examples)
- [Tips & Tricks](#tips--tricks)
- [FAQ](#faq)
- [Troubleshooting](#troubleshooting)

## Quick Start

1. **Launch Yoyo**: Run the application using `./gradlew run`
2. **Add your first task**: Type `todo Buy groceries`
3. **View all tasks**: Type `list`
4. **Mark tasks complete**: Type `mark 1`
5. **Get help anytime**: Type `help`

## Features

### 📝 Task Management
Yoyo supports three types of tasks to help you stay organized:

#### Todo Tasks
Simple tasks without deadlines
```
todo Buy groceries
todo Read a book
todo Call mom
```

#### Deadline Tasks
Tasks that must be completed by a specific date
```
deadline Submit report /by 2025-09-20
deadline Finish project /by 2025-12-31
```

#### Event Tasks
Tasks with specific start and end times
```
event Team meeting /from 2025-09-20 1400 /to 2025-09-20 1600
event Conference /from 2025-10-15 0900 /to 2025-10-15 1700
```

### 🔍 View & Search
Keep track of your tasks with powerful viewing and searching capabilities:

#### List All Tasks
```
list
```
Shows all your tasks with their status, type, and details.

#### Find Tasks
```
find meeting
find report
find urgent
```
Search for tasks containing specific keywords.

#### Sort Tasks
```
sort date asc
sort description desc
sort status asc
sort type asc
```
Sort your tasks by date, description, completion status, or task type.

### ✏️ Task Modification
Update your tasks as your plans change:

#### Mark as Complete
```
mark 1
mark 3
```
Mark tasks as done using their task numbers.

#### Mark as Incomplete
```
unmark 1
unmark 2
```
Mark completed tasks as not done.

#### Delete Tasks
```
delete 1
delete 5
```
Remove tasks you no longer need.

### 🚪 System Commands
Control the application with these commands:

#### Get Help
```
help
```
Shows all available commands and usage examples.

#### Exit Application
```
bye
quit
exit
```
All three commands will close the application gracefully.

## Command Reference

| Command | Description | Example |
|---------|-------------|---------|
| `todo <description>` | Add a simple task | `todo Buy groceries` |
| `deadline <desc> /by <date>` | Add task with deadline | `deadline Submit report /by 2025-09-20` |
| `event <desc> /from <start> /to <end>` | Add event with time range | `event Meeting /from 2pm /to 4pm` |
| `list` | Show all tasks | `list` |
| `find <keyword>` | Search tasks | `find meeting` |
| `sort <criteria> [asc\|desc]` | Sort tasks | `sort date asc` |
| `mark <number>` | Mark task complete | `mark 1` |
| `unmark <number>` | Mark task incomplete | `unmark 1` |
| `delete <number>` | Delete task | `delete 1` |
| `help` | Show help | `help` |
| `bye` / `quit` / `exit` | Exit app | `bye` |

## Examples

### Daily Workflow
```
todo Buy groceries
todo Call dentist
deadline Submit assignment /by 2025-09-25
event Team standup /from 2025-09-18 0900 /to 2025-09-18 0930
list
mark 1
find assignment
```

### Project Management
```
todo Plan project timeline
deadline Complete research /by 2025-10-01
event Client presentation /from 2025-10-15 1400 /to 2025-10-15 1500
todo Prepare slides
todo Practice presentation
sort date asc
mark 4
```

### Personal Organization
```
todo Clean room
todo Pay bills
deadline Renew passport /by 2025-11-30
event Family dinner /from 2025-09-21 1800 /to 2025-09-21 2100
find bills
mark 2
```

## Tips & Tricks

### 💡 Pro Tips
- **Use descriptive task names**: Clear descriptions make it easier to find tasks later
- **Be specific with dates**: Use YYYY-MM-DD format for consistent date handling
- **Regular reviews**: Use `list` frequently to stay on top of your tasks
- **Search effectively**: Use `find` with specific keywords to locate tasks quickly
- **Sort strategically**: Use `sort date asc` to see upcoming deadlines first

### ⌨️ Keyboard Shortcuts
- **Enter**: Send command
- **Up/Down arrows**: Navigate through command history (if supported)

### 📅 Date Formats
- **Deadline dates**: `YYYY-MM-DD` (e.g., `2025-09-20`)
- **Event times**: `YYYY-MM-DD HHmm` (e.g., `2025-09-20 1400`)
- **Natural language**: You can also use formats like `today`, `tomorrow`, or `next week`

## FAQ

### 🤔 Frequently Asked Questions

**Q: How do I edit a task after creating it?**
A: Currently, you cannot directly edit tasks. Instead, delete the old task and create a new one with the corrected information.

**Q: What happens if I enter an invalid date?**
A: Yoyo will show an error message and ask you to use the correct date format (YYYY-MM-DD for deadlines, YYYY-MM-DD HHmm for events).

**Q: Can I have multiple tasks with the same name?**
A: Yes, Yoyo allows duplicate task names. Each task gets a unique number for identification.

**Q: How many tasks can I store?**
A: There's no strict limit, but for optimal performance, we recommend keeping under 1000 tasks.

**Q: Does Yoyo save my tasks automatically?**
A: Yes! All your tasks are automatically saved to a file called `yoyo.txt` in the data directory.

**Q: What if I accidentally delete a task?**
A: Deleted tasks cannot be recovered. Make sure to double-check before using the `delete` command.

**Q: Can I use Yoyo on different devices?**
A: Yes, but you'll need to copy the `yoyo.txt` file to share your tasks between devices.

## Troubleshooting

### 🔧 Common Issues & Solutions

#### "OOPS!!! I'm sorry, but I don't know what that means :-("
**Cause**: You entered an unrecognized command.
**Solution**: Type `help` to see all available commands, or check your spelling.

#### "Invalid task number"
**Cause**: You're trying to mark/unmark/delete a task that doesn't exist.
**Solution**: Use `list` to see all tasks and their numbers, then use the correct number.

#### "A todo needs a description"
**Cause**: You tried to create a todo without providing a description.
**Solution**: Add a description after the `todo` command, e.g., `todo Buy milk`.

#### "Usage: deadline <description> /by <yyyy-MM-dd>"
**Cause**: Incorrect deadline command format.
**Solution**: Use the format: `deadline <description> /by <YYYY-MM-DD>`

#### "Usage: event <description> /from <yyyy-MM-dd HHmm> /to <yyyy-MM-dd HHmm>"
**Cause**: Incorrect event command format.
**Solution**: Use the format: `event <description> /from <YYYY-MM-DD HHmm> /to <YYYY-MM-DD HHmm>`

#### Tasks not saving
**Cause**: File write permissions or disk space issues.
**Solution**: Check that you have write permissions in the application directory and sufficient disk space.

### 📞 Getting Help
If you encounter issues not covered here:
1. Try the `help` command within Yoyo
2. Check the examples in this guide
3. Restart the application
4. Ensure you're using the correct command syntax

---

**Yoyo** - Your friendly task management companion! 🎉

*Last updated: September 18, 2025*