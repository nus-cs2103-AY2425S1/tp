<variable name="jarFile">
VBook-release-1.4.jar
</variable>

<variable name="commandSummary">

### Command Summary
| Action     | Format                                                                                           | Examples                                                                                                                                        |
   |------------|--------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------|
| **Add**    | `:add -n NAME -p PHONE_NUMBER -e EMAIL -l LOCATION -t TAG -r REMARK…​` <br> `:a -n NAME ...`     | `:add -n James Ho -p 22224444 -e jamesho@example.com -l 123, Clementi Rd, 1234665 -t friend -r My favourite colleague` <br> `:a -n James Ho...` |
| **Clear**  | `:clear`                                                                                         | `:clear`                                                                                                                                        |
| **Delete** | `:remove -i INDEX`<br/>`:rm -i INDEX1, INDEX2, ...`                                              | `:remove -i 3,4,5`<br/>`:rm -i 3,4,5`                                                                                                           |
| **Edit**   | `:edit INDEX [-n NAME] [-p PHONE] [-e EMAIL] [-a ADDRESS] [-t TAG]…​`<br/>`:ed INDEX -n NAME...` | `:edit 2 -n James Lee -e jameslee@example.com`<br/>`:ed 2 -n Joshua...`                                                                         |
| **Exit**   | `:exit`                                                                                          | `:exit`                                                                                                                                         |
| **Export** | `:export`                                                                                        | `:export`                                                                                                                                       |
| **Find**   | `:find [-n NAME] [-p PHONE] [-e EMAIL] [-a ADDRESS] [-t TAG]…​`                                  | `:find -n david -l serangoon`                                                                                                                   |
| **Help**   | `:help`                                                                                          | `:help`                                                                                                                                         |
| **List**   | `:list`<br/>`:ls`                                                                                | `:list`<br/>`:ls`                                                                                                                               |
| **Redo**   | `:redo`                                                                                          | `:redo`                                                                                                                                         |
| **Undo**   | `:undo`                                                                                          | `:undo`                                                                                                                                         |
</variable>

<variable name="example">
To inject this HTML segment in your markbind files, use {{ example }} where you want to place it.
More generally, surround the segment's id with double curly braces.
</variable>
