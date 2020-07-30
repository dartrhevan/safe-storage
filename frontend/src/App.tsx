import React from 'react';
import clsx from 'clsx';
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
import {createStyles, makeStyles, Theme} from '@material-ui/core/styles'
import MenuIcon from '@material-ui/icons/Menu';
import MailIcon from '@material-ui/icons/Mail';
import AuthDialog from "./components/AuthDialog";
import {useStore, useDispatch} from 'react-redux';
import AdaptiveDrawer from './components/AdaptiveDrawer';
import Note from './model/Note';
import INoteState from './store/Note/INoteState';
//import store from './store/store';

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
        content: {
            marginLeft: drawerWidth
        },
        contentShift: {
            transition: theme.transitions.create('margin', {
                easing: theme.transitions.easing.easeOut,
                duration: theme.transitions.duration.enteringScreen,
            }),
            marginRight: 0,
        },
        button: {
            backgroundColor: theme.palette.primary.main
        }
    }),
);

function App() {
    const classes = useStyles();
    const [openDrawer, setOpenDrawer] = React.useState(false);

    const store = useStore();
    const [openDialog, setOpenDialog] = React.useState(store.getState().auth.username === '');
    const [notes, setNotes] = React.useState<INoteState>(store.getState().notes);

    store.subscribe(() => {
        setNotes(store.getState().notes);
    });
    function close() {
        setOpenDialog(false);
    }

    //useEffect(...)
    //getUsername from api
    //

    const dispatch = useDispatch();

    const toggleDrawer = () => {
        setOpenDrawer(!openDrawer);
    };

    console.log(store.getState());

    return (
        <div className={classes.root}>
            <AppBar color="inherit" className={classes.appBar} position="fixed">
                <Toolbar>

                    <Hidden smUp implementation="css">
                        <IconButton edge="start" className={classes.menuButton} color="inherit" aria-label="menu">
                            <MenuIcon onClick={toggleDrawer}/>
                        </IconButton>
                    </Hidden>
                    <Typography variant="h6" className={classes.title}>
                        {store.getState().auth.username}
                    </Typography>
                    <Button className={classes.button} color="inherit">Logout</Button>
                </Toolbar>
            </AppBar>
            <div>
                <AdaptiveDrawer open={openDrawer}>
                    <Toolbar/>
                    <List>
                        {notes.list.map((note: Note, index: number) => (
                            <ListItem button key={note.id as string}>
                                <ListItemIcon><MailIcon/></ListItemIcon>
                                <ListItemText primary={note.head}/>
                            </ListItem>
                        ))}
                    </List>
                </AdaptiveDrawer>
                <AuthDialog open={openDialog} close={close}/>
                <Typography className={classes.content}>
                    <Toolbar />Some text</Typography>
            </div>
        </div>
    );
}

export default App;
