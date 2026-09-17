# Sage User Guide

Sage is a friendly command-line task manager for todos, deadlines, and events.
It remembers your tasks between sessions and helps you find, complete, and
remove them.

## Getting started

Run Sage from your IDE or with the project's Gradle run task. Sage will greet
you and wait for a command. Commands are case-sensitive and must be entered in
lowercase. Separate words with spaces.

Tasks are numbered in the order they appear in the list, starting at **1**.
The first task is always task `1` (not task `0`). Use the number shown by
`list` with `mark`, `unmark`, and `delete`.

## Adding tasks

### Todo

Use `todo` for a task without a date or time:

```text
todo return library books
```

### Deadline

Use `deadline` followed by `/by` and a date or date and time. Sage accepts
these formats:

- `d/M/yyyy`, such as `15/10/2019`
- `yyyy-MM-dd`, such as `2019-10-15`
- `d/M/yyyy HHmm`, such as `15/10/2019 1800`
- `yyyy-MM-dd HHmm`, such as `2019-10-15 1800`

Times use the 24-hour `HHmm` format. A date-only deadline is due at the end
of that day:

```text
deadline submit assignment /by 25/12/2025 2359
```

### Event

Use `event` with `/from` and `/to` date or date-time values. Sage accepts either
of these formats:

- Date only: `d/M/yyyy` (for example, `25/12/2025`)
- Date and time: `d/M/yyyy HHmm` (for example, `25/12/2025 1530`)

The ISO equivalents `yyyy-MM-dd` and `yyyy-MM-dd HHmm` are also accepted.
An Event cannot contain a time without a date.

Date-only event boundaries cover the full day: a start date uses `0000` and an
end date uses `2359`.

The end date and time must be later than the start date and time:

```text
event team meeting /from 25/12/2025 1400 /to 25/12/2025 1530
```

Sage saves each successfully added task automatically. It rejects tasks with
no description, invalid dates or times, duplicate details, or missing date-time
fields. A deadline must contain exactly one `/by` field. An event must contain
exactly one `/from` field and one `/to` field.

## Viewing and finding tasks

| Command | Description | Example |
| --- | --- | --- |
| `list` | Shows all tasks in their current order. | `list` |
| `find KEYWORD` | Shows tasks whose descriptions contain `KEYWORD`. | `find books` |
| `on DATE` | Shows deadlines and events on `DATE`. | `on 25/12/2025` |
| `duplicates` | Reports groups of duplicate tasks. | `duplicates` |

For `on`, use the date format `d/M/yyyy`. An event is shown for every date
between its start and end dates, inclusive. The `find` keyword is case-sensitive,
so `find Books` and `find books` can return different results.

Duplicate detection ignores case and extra surrounding or repeated whitespace
in descriptions. For deadlines and events, the date or time range must also
match. Completion status does not affect whether tasks are duplicates. The
`duplicates` command does not accept arguments.

## Managing tasks

First enter `list` to see the task numbers. Then use one of these commands,
replacing `TASK_NUMBER` with a positive number such as `1`:

| Command | What it does | Example |
| --- | --- | --- |
| `mark TASK_NUMBER` | Marks the task as done. | `mark 1` |
| `unmark TASK_NUMBER` | Changes a completed task back to not done. | `unmark 1` |
| `delete TASK_NUMBER` | Permanently removes the task. | `delete 1` |

For example:

```text
list
mark 1
```

Each command requires a valid positive task number from the current list.

## Ending the session

Enter `bye` when you are finished:

```text
bye
```

Sage says goodbye and closes the session. Tasks already saved remain available
the next time you start Sage.

## Quick reference

```text
todo DESCRIPTION
deadline DESCRIPTION /by DATE
deadline DESCRIPTION /by DATE HHmm
event DESCRIPTION /from DATE /to DATE
event DESCRIPTION /from DATE HHmm /to DATE HHmm
list
find KEYWORD
on d/M/yyyy
duplicates
mark TASK_NUMBER
unmark TASK_NUMBER
delete TASK_NUMBER
bye
```

For `DATE`, the accepted formats are:

- Date only: `d/M/yyyy` (for example, `25/12/2025`)
- Date only: `yyyy-MM-dd` (for example, `2025-12-25`)
- Date and time: `d/M/yyyy HHmm` (for example, `25/12/2025 1530`)
- Date and time: `yyyy-MM-dd HHmm` (for example, `2025-12-25 1530`)

`HHmm` uses 24-hour time and cannot be entered without a date. The
hyphenated day-first format `25-12-2025` is not accepted.
