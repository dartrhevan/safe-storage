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
import AuthDialog from "./components/AuthDialog";
import { useStore, useDispatch } from 'react-redux';
//Remove
interface IAuth {
    username: string
}

interface INote {

}

interface IState {
    auth: IAuth
    note: INote
}
//

const drawerWidth = 300;

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
            width: drawerWidth,
            flexShrink: 1,
        },
        drawerPaper: {
            width: drawerWidth,
        },
        drawerContainer: {
            overflow: 'auto',
        },
    }),
);

function App() {
  const classes = useStyles();
    const [openDrawer, setOpenDrawer] = React.useState(false);

    const store = useStore();
    const [openDialog, setOpenDialog] = React.useState(store.getState().auth.username === '');
    function close() {
        setOpenDialog(false);
    }
    const dispatch = useDispatch();

    const toggleDrawer = () => {
        setOpenDrawer(!openDrawer);
    };
    console.log(store.getState());

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
              {store.getState().auth.username}
          </Typography>
          <Button color="inherit">Logout</Button>
        </Toolbar>
      </AppBar>
        <div>
            <Hidden smUp implementation="css">

                <Drawer
                    className={classes.drawer}
                    variant="persistent"
                    open={openDrawer}
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
            <AuthDialog open={openDialog} close={close}/>
        </div>
    </div>
  );
}

export default App;
