UPDATE Task 
SET isCompleted = true 
WHERE name = 'Task 1';

UPDATE Task 
SET description = 'Updated Description of Task 2'
WHERE name = 'Task 2';

UPDATE Task 
SET name = 'Updated Task 3', 
    description = 'Updated Description of Task 3'
WHERE name = 'Task 3';