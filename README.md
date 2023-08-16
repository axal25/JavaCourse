# JavaCourse

JavaCourse

### Git

1. Clone remote repository to local drive  
   `git clone https://github.com/axal25/JavaCourse.git`
2. Make changes
3. Add changes to the index  
   `git add .`
4. Verify the added changes  
   `git status`
6. Commit changes with message  
   `git commit -m 'msg'`
7. Verify commited changes  
   `git status`
8. Push changes made to remote repository branch called **master**  
   `git push -u origin master`  
   In future main branch can change from ~~master~~ to **main**  
   `git push -u origin main`

### Pass

1. `pass -c Git/axal25`

#### Other git useful information

1. Tracked files
    1. Remove added to index file  
       `git rm path/to/file`
    2. Remove file/folder from the cached index if necessary  
       `git rm --cached rel/file/path`  
       or for directory (recursively)  
       `git rm --cached -r rel/dir/path`
    3. Reset git local to previous local commit  
       `git reset HEAD~`
2. Branches
    1. List branches
        1. `git branch`
        2. or `git branch -a`
        3. or `git branch --list`
        4. or `git branch -l`
    2. Create new branch
        1. off of branch we are currently on \
           `git branch <branch_name>`
        2. off of branch <branch_name_source> \
           `git branch <branch_name_new> <branch_name_source>`
    3. Switch to branch
        1. `git checkout <branch_name>`
        2. or `git switch <branch_name>`
    4. Create new branch and switch to it
        1. `git checkout -b <branch_name>`
        2. `git switch -c <branch_name>`
        3. or
            1. `git branch <branch_name>`
            2. and `git checkout <branch_name>`
        4. or
            1. `git branch <branch_name>`
            2. and `git switch <branch_name>`
    5. Remove local branch  
       `git branch -d <branch_name>`
    6. Forcefully remove local branch  
       `git branch -D <branch_name>`
    7. Push new branch to remote repository (when new branch does not exist on remote rep)
        1. `git push -u origin <branch_name_new_local>:<branch_name_new_remote>`
        2. or `git push -u origin <branch_name_new_local_and_remote>`
3. Restore/check-out file to some version \
    1. `git checkout -- </path/to/file>`
    2. or `git restore <path/to/file>`
4. Stashing
    1. List stashes  
       `git stash list`
    2. Stash current uncommited changes  
       `git stash -m 'message'`
    3. Reverse stashed-in changes (apply changes back in again)
        1. when there is only 1 stash  
           `git stash apply`
        2. when there are more than 1 stash
            1. `git stash apply stash@{<stash_number>}`
            2. or `git stash apply <stash_number>`
    4. Remove stash (stashed changes)
        1. when there is only 1 stash  
           `git stash drop`
        2. when there are more than 1 stash
            1. `git stash drop stash@{<stash_number>}`
            2. or `git stash drop <stash_number>`
5. Show commit history  
   `git log --oneline --graph`

```text
* <commit_nb> (HEAD -> master, origin/master, origin/HEAD, <branch_name_target>) <commit_msg>
...
```

# Troubles

1. Git push not working - ~~master~~ is now **main**
    1. Trouble scenario  
       `$ git push -u origin master`  
       `error: src refspec master does not match any`
       `error: failed to push some refs to 'https://github.com/axal25/{...}.git'`
    1. Fix
        1. Show references  
           `$ git show-ref`
           `17767a39e8f6779be8889c966c591078878e40ef refs/heads/main`
           `9cf3829b2d50636c2814b08b57c8c0893bfd3630 refs/remotes/origin/HEAD`
           `9cf3829b2d50636c2814b08b57c8c0893bfd3630 refs/remotes/origin/main`
        1. Try pushing local HEAD ref to remote master ref  
           `git push -u origin HEAD:master`
        1. This will creation a remote branch 'master' and push commit there, pull request, merge request required\
2. Exclude files from being formatted: \
   `File > Settings > Editor > Code Style > Formatter > Do not format:`

```text
src/main/java/interfaceVsAbstractClass/*.java
src/test/java/strings/StringsTest.java
```