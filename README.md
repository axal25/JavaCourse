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

1. Remove added to index file  
   `git rm path/to/file`
2. Remove file/folder from the cached index if necessary  
   `git rm --cached rel/file/path`  
   or for directory (recursively)  
   `git rm --cached -r rel/dir/path`
3. Reset git local to previous local commit  
   `git reset HEAD~`
4. List branches  
   `git branch`
5. Crete new branch  
   `git checkout -b <branchname>`
6. Switch to existing branch  
   `git checkout <branch_name>`
7. Remove local branch  
   `git branch -d <branch_name>`
8. Forcefully remove local branch  
   `git branch -D <branch_name>`
9. List stashes  
   `git stash list`
10. Stash current uncommited changes  
    `git stash -m 'message'`
11. Reverse stashed-in changes (apply changes back in again)
    1. when there is only 1 stash  
       `git stash apply`
    1. when there are more than 1 stash  
       `git stash apply stash@{<stash_number>}`
12. Remove stash (stashed changes)
    1. when there is only 1 stash  
       `git stash drop`
    1. when there are more than 1 stash  
       `git stash drop stash@{<stash_number>}`
13. Branching
    1. list all branches  
       `git branch -a`
    1. switch to different branch  
       `git switch <branch_name>`
    1. creates a branch off of current branch we are on  
       `git branch <branch_name>`
    1. creates a branch off of branch with name <branch_name_source>
       `git branch <branch_name_target> <branch_name_source>`
    1. show log  
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
        1. This will creation a remote branch 'master' and push commit there, pull request, merge request required