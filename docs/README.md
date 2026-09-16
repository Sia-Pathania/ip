# Sage User Guide

Sage is a friendly task manager that helps you keep track of todos, deadlines,
and events. Type a command at the prompt and Sage will update your task list.
Your tasks are saved automatically, so they are available the next time you
start Sage.

## Getting started

Start Sage using your IDE or the project's Gradle run task. Sage will greet you
and then wait for a command. Commands are case-sensitive and should be entered
in lowercase.

Each task is shown with a number. Use that number when marking, unmarking, or
deleting a task.

## Adding tasks

### Add a todo

Use `todo` for a task without a date or time:

```text
todo return library books
```

### Add a deadline

Use `deadline` followed by `/by` and a date and time. The format is
`d/M/yyyy HHmm`, using 24-hour time:

```text
deadline submit assignment /by 25/12/2025 2359
```

### Add an event

Use `event` with `/from` and `/to` date-time values. The end must be later than
the start:

```text
event team meeting /from 25/12/2025 1400 /to 25/12/2025 1530
```

Sage rejects tasks with no description, invalid dates or times, duplicate tasks,
or missing date-time fields. A deadline must have exactly one `/by` field, and
an event must have exactly one `/from` and one `/to` field.

## Viewing and finding tasks

| Command | What it does | Example |
| --- | --- | --- |
| `list` | Shows every task in the order it was added | `list` |
| `find KEYWORD` | Shows tasks whose descriptions contain the keyword | `find books` |
| `on DATE` | Shows deadlines and events on a date | `on 25/12/2025` |
| `duplicates` | Finds groups of duplicate tasks | `duplicates` |

For `on`, use the date format `d/M/yyyy`. An event is shown for every date
between its start and end dates, inclusive. The `find` command matches the
keyword as entered, so `find Books` and `find books` can produce different
results.

Duplicate detection compares descriptions without case or extra surrounding
and repeated whitespace. For deadlines and events, the date or time range must
also match. Completion status does not affect whether tasks are duplicates.

## Managing tasks

Use the task number displayed by `list`:

```text
mark 1
unmark 1
delete 1
```

`mark` marks a task as done, `unmark` changes it back to not done, and `delete`
removes it permanently from the list. For all three commands, the task number
must be a valid positive number in the current list.

## Ending Sage

When you are finished, enter:

```text
bye
```

Sage says goodbye and closes the session.

## Quick reference

```text
todo DESCRIPTION
deadline DESCRIPTION /by d/M/yyyy HHmm
event DESCRIPTION /from d/M/yyyy HHmm /to d/M/yyyy HHmm
list
find KEYWORD
on d/M/yyyy
duplicates
mark TASK_NUMBER
unmark TASK_NUMBER
delete TASK_NUMBER
bye
```
