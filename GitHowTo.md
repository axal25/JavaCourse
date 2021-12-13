# Git How To

1. Clone remote repository to local drive  
   `git clone https://github.com/axal25/TDDDemo.git`
2. Make changes
3. Add changes to the index  
   `git add .`
4. Verify the added changes  
   `git status`
5. Commit changes with message  
   `git commit -m 'msg'`
6. Verify commited changes  
   `git status`
7. Push changes to remote repository branch called ~~master~~ **main**  
   `git push -u origin main`  
   GitHub changed ~~master~~ to **main**  
   ~~`git push -u origin master`~~

## Pass

pass pass -c Git/axal25

### Git - Useful

1. Remove added to index file  
   `git rm path/to/file`
1. Reset git local to previous local commit  
   `git reset HEAD~`
1. List branches  
   `git branch`
1. Crete new branch  
   `git checkout -b <branchname>`
1. Switch to existing branch  
   `git checkout <branch_name>`
1. Remove local branch  
   `git branch -d <branch_name>`
1. Forcefully remove local branch  
   `git branch -D <branch_name>`
1. List stashes  
   `git stash list`
1. Stash current uncommited changes  
   `git stash`
1. Reverse stashed-in changes (apply changes back in again)
    1. when there is only 1 stash  
       `git stash apply`
    1. when there are more than 1 stash  
       `git stash apply stash@{<stash_number>}`
1. Remove stash (stashed changes)
    1. when there is only 1 stash  
       `git stash drop`
    1. when there are more than 1 stash  
       `git stash drop stash@{<stash_number>}`

# Troubles

1. Git push not working - ~~master~~ is now **main**
    1. Trouble scenario  
       `$ git push -u origin master`  
       `error: src refspec master does not match any`
       `error: failed to push some refs to 'https://github.com/axal25/`DockAuto.git'`
    1. Fix
        1. Show references  
           `$ git show-ref`
           `17767a39e8f6779be8889c966c591078878e40ef refs/heads/main`
           `9cf3829b2d50636c2814b08b57c8c0893bfd3630 refs/remotes/origin/HEAD`
           `9cf3829b2d50636c2814b08b57c8c0893bfd3630 refs/remotes/origin/main`
        1. Try pushing local HEAD ref to remote master ref  
           `git push -u origin HEAD:master`
        1. This will creation a remote branch 'master' and push commit there, pull request, merge request required

