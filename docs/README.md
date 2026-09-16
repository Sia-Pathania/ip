# Sage User Guide

// Update the title above to match the actual product name

// Product screenshot goes here

// Product intro goes here

## Adding deadlines

// Describe the action and its outcome.

// Give examples of usage

Example: `keyword (optional arguments)`

// A description of the expected outcome goes here

```
expected output
```

## Feature ABC

// Feature details


## Finding duplicate tasks

Use `duplicates` to find groups of repeated tasks. Descriptions are compared
case-insensitively after trimming surrounding whitespace and collapsing
repeated internal whitespace. Todo tasks use an empty time range; deadlines
use their deadline time; and events use both their start and end times.
Completion status is ignored, and the command only reports results.

Example: `duplicates`

Sage displays each duplicate group with the tasks' existing list numbers. If
no duplicates are found, Sage displays `There are no duplicate tasks.`.

The command does not accept arguments.

// Feature details
