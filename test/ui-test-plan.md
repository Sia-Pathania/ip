# UI Test Plan

Run every case as a fresh session with Java 25.

For each case, run from a fresh temporary working directory so the relative
`data/sage.txt` file starts empty. This also verifies that the data directory
is created automatically.

Dates and times are treated as strings; no date/time conversion is expected.

Launch command:

```text
javac -d build/classes/java/main src/main/java/*.java && java -cp build/classes/java/main Sage
```

The standard startup banner is expected at the beginning of every session:

```text
____________________________________________________________
 ____                   
/ ___|  __ _  __ _  ___ 
\___ \ / _` |/ _` |/ _ \
 ___) | (_| | (_| |  __/
|____/ \__,_|\__, |\___|
             |___/      
Hello! I'm Sage.
I'm here whenever you feel like chatting!
____________________________________________________________
What can I do for you?
____________________________________________________________
```

## Test case 1: Adding a Todo

**Aim:** Verify that Sage adds a Todo and reports its count.

**Input:**
```text
todo read book
bye
```

**Expected output:**
```text
____________________________________________________________
Got it. I've added this task:
  [T][ ] read book
Now you have 1 task in the list.
____________________________________________________________
____________________________________________________________
Bye. Hope to see you again soon!
____________________________________________________________
```

## Test case 2: Adding a Deadline

**Aim:** Verify that Sage adds a Deadline with its due date.

**Input:**
```text
deadline submit report /by Friday
bye
```

**Expected output:**
```text
____________________________________________________________
Got it. I've added this task:
  [D][ ] submit report (by: Friday)
Now you have 1 task in the list.
____________________________________________________________
____________________________________________________________
Bye. Hope to see you again soon!
____________________________________________________________
```

## Test case 3: Adding an Event

**Aim:** Verify that Sage adds an Event with its start and end times.

**Input:**
```text
event team meeting /from Monday 10am /to Monday 11am
bye
```

**Expected output:**
```text
____________________________________________________________
Got it. I've added this task:
  [E][ ] team meeting (from: Monday 10am to: Monday 11am)
Now you have 1 task in the list.
____________________________________________________________
____________________________________________________________
Bye. Hope to see you again soon!
____________________________________________________________
```

## Test case 4: Listing Todo, Deadline, and Event tasks

**Aim:** Verify that `list` displays all three task types in insertion order.

**Input:**
```text
todo read book
deadline submit report /by Friday
event team meeting /from Monday 10am /to Monday 11am
list
bye
```

**Expected output:**
```text
____________________________________________________________
Here are the tasks in your list:
1.[T][ ] read book
2.[D][ ] submit report (by: Friday)
3.[E][ ] team meeting (from: Monday 10am to: Monday 11am)
____________________________________________________________
____________________________________________________________
Bye. Hope to see you again soon!
____________________________________________________________
```

## Test case 5: Marking a task as done

**Aim:** Verify that `mark 1` marks the first task as done.

**Input:**
```text
todo read book
mark 1
bye
```

**Expected output:**
```text
____________________________________________________________
Nice! I've marked this task as done:
  [T][X] read book
____________________________________________________________
____________________________________________________________
Bye. Hope to see you again soon!
____________________________________________________________
```

## Test case 6: Unmarking a task

**Aim:** Verify that `unmark 1` changes a completed task back to not done.

**Input:**
```text
todo read book
mark 1
unmark 1
bye
```

**Expected output:**
```text
____________________________________________________________
OK, I've marked this task as not done yet:
  [T][ ] read book
____________________________________________________________
____________________________________________________________
Bye. Hope to see you again soon!
____________________________________________________________
```

## Test case 7: Exiting with bye

**Aim:** Verify that `bye` ends the session with the farewell message.

**Input:**
```text
bye
```

## Test case 8: Deleting a task persists

**Aim:** Verify that deleting a task is saved to disk.

**Input:**
```text
todo temporary task
delete 1
list
bye
```

**Expected output:**
```text
Here are the tasks in your list:
____________________________________________________________
```

## Test case 9: Loading tasks after restart

**Aim:** Verify that tasks saved by one session are loaded by the next session.

**Input:**
```text
todo remember this
bye
```

Then start Sage again in the same working directory and enter:

```text
list
bye
```

**Expected output in the second session:**
```text
Here are the tasks in your list:
1.[T][ ] remember this
____________________________________________________________
```

**Expected output:**
```text
____________________________________________________________
Bye. Hope to see you again soon!
____________________________________________________________
```
