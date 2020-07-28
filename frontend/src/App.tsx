import React from 'react';
import {
    AppBar,
    Button,
    Drawer, Hidden,
    IconButton,
    List,
    ListItem,
    ListItemIcon,
    ListItemText,
    Toolbar,
    Typography
} from '@material-ui/core';
import { createStyles, makeStyles, Theme } from '@material-ui/core/styles'
import MenuIcon from '@material-ui/icons/Menu';
import MailIcon from '@material-ui/icons/Mail';
import './App.css';

const useStyles = makeStyles((theme: Theme) =>
    createStyles({
      root: {
        flexGrow: 1,
      },
        appBar: {
            zIndex: theme.zIndex.drawer + 1,
        },
      menuButton: {
        marginRight: theme.spacing(2),
      },
      title: {
        flexGrow: 1,
      },
        drawer: {
            width: 300,
            flexShrink: 1,
        },
        drawerPaper: {
            width: 300,
        },
        drawerContainer: {
            overflow: 'auto',
        },
    }),
);

function App() {
  const classes = useStyles();
    const [open, setOpen] = React.useState(false);

   /* const handleDrawerOpen = () => {
        setOpen(true);
    };
*/
    const toggleDrawer = () => {
        setOpen(!open);
    };

    return (
    <div className={classes.root}>
      <AppBar className={classes.appBar} position="fixed">
        <Toolbar>

            <Hidden smUp implementation="css">
          <IconButton edge="start" className={classes.menuButton} color="inherit" aria-label="menu">
            <MenuIcon onClick={toggleDrawer} />
          </IconButton>
            </Hidden>
          <Typography variant="h6" className={classes.title}>
            News
          </Typography>
          <Button color="inherit">Login</Button>
        </Toolbar>
      </AppBar>
        <div>
            <Hidden smUp implementation="css">

                <Drawer
                    className={classes.drawer}
                    variant="persistent"
                    open={open}
                    classes={{
                        paper: classes.drawerPaper,
                    }}>

                    <Toolbar />
                    <List>
                        {['Inbox', 'Starred', 'Send email', 'Drafts'].map((text, index) => (
                            <ListItem button key={text}>
                                <ListItemIcon><MailIcon /></ListItemIcon>
                                <ListItemText primary={text} />
                            </ListItem>
                        ))}
                    </List>
                </Drawer>
            </Hidden>

            <Hidden xsDown implementation="css">
            <Drawer
                className={classes.drawer}
                variant="permanent"
                classes={{
                    paper: classes.drawerPaper,
                }}>

                <Toolbar />
                <List>
                    {['Inbox', 'Starred', 'Send email', 'Drafts'].map((text, index) => (
                        <ListItem button key={text}>
                            <ListItemIcon><MailIcon /></ListItemIcon>
                            <ListItemText primary={text} />
                        </ListItem>
                    ))}
                </List>
            </Drawer>
            </Hidden>
        </div>
    </div>
  );
}

export default App;
