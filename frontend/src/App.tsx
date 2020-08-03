import React, {MouseEvent, ChangeEvent, useEffect, SyntheticEvent } from 'react';
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
    Typography,
    TextField,
    Snackbar
} from '@material-ui/core';
import {createStyles, makeStyles, Theme} from '@material-ui/core/styles'
import MenuIcon from '@material-ui/icons/Menu';
import DeleteIcon from '@material-ui/icons/Delete';
import SaveIcon from '@material-ui/icons/Save';
import MailIcon from '@material-ui/icons/Mail';
import AuthDialog from "./components/AuthDialog";
import {useStore, useDispatch} from 'react-redux';
import AdaptiveDrawer from './components/AdaptiveDrawer';
import {setUsername as setStateUsername} from './store/Auth/actions';
import Note from './model/Note';
import INoteState from './store/Note/INoteState';
import { getNoteDetails, editNote, removeNote, addNote, listNotes, logout, getUsername } from './api';
import { TextareaAutosize } from '@material-ui/core';
import { UpdateList } from './store/Note/actions';
import { Logout } from './store/Auth/actions';
import { Alert } from '@material-ui/lab';
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
            padding: theme.spacing(4),
        },
        field: {
            marginTop: theme.spacing(2),
            marginBottom: theme.spacing(2),
        },
        contentShift: {
            marginLeft: drawerWidth
        },
        button: {
            margin: theme.spacing(2),
        }
    }),
);

function App() {
    const classes = useStyles();
    const [openDrawer, setOpenDrawer] = React.useState(true);

    const store = useStore();
    const [openDialog, setOpenDialog] = React.useState<boolean>(store.getState().auth.username === '');
    const [notes, setNotes] = React.useState<INoteState>(store.getState().notes);

    const [alertMessage, setAlertMessage] = React.useState<string | null>(null);
    const [currentNote, setCurrentNote] = React.useState<Note | null>(null);
    const [head, setHead] = React.useState<string>((currentNote?.head) as string);
    //const head = currentNote?.head;
    const [text, setText] = React.useState<string>((currentNote?.text) as string);
    //const text = currentNote?.text;
    store.subscribe(() => {
        setNotes(store.getState().notes);
        setOpenDialog(store.getState().auth.username === '');
    });

    function onTextChange(event: ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) {
        setText(event.target.value);
    }

    function onHeadChange(event: ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) {
        setHead(event.target.value);
    }

    function onCloseDialog() {
        setOpenDialog(false);
    }

    function onCancel() {
        setCurrentNote(null);
        setHead("head");//TODO: extract vars
        setText("text");
    }

    function alert(message: string) {
        setAlertMessage(message);
    }

    function updateList() {
        return listNotes().then(res => {///TODO: extract hook
            const resp = res as Response;
            console.log(resp)
            if(!res || resp.status !== 200)
                alert('Error!');
            else
                return resp.json();
        }).then(list => dispatch(UpdateList(list)));///
    }

    function onSave() {
        if(currentNote) { //edit note
            currentNote.head = head;
            currentNote.text = text;
            currentNote.date = new Date();
            editNote(currentNote)
                .then(r => updateList());
        }
        else {// add new note
            /*currentNote.head = head;
            currentNote.text = text;
            currentNote.date = new Date();*/
            addNote(new Note(head, text, null, new Date()))
                .then(r => updateList());
        }
        onCancel();
    }

    function deleteNote() {
        if(currentNote)
            removeNote(currentNote.id as string)
                .then(r => updateList());
        onCancel();
    }

    useEffect(() => {
        getUsername()
            .then((r: void | Response) => {
                const resp = r as Response;
                console.log(resp)
                return r && resp.status === 200 ? resp.text() : "";
            }).then(username => {
                dispatch(setStateUsername(username));
                if(username !== "") {
                    console.log(store.getState());
                    onCloseDialog();
                    listNotes().then(res => {///TODO: extract hook
                        const resp = res as Response;
                        console.log(resp)
                        if (!res || resp.status !== 200) {
                            alert('Autolist Error!');
                            return null;
                        }
                        else
                            return resp.json();
                    }).then(list => dispatch(UpdateList(list)));///;
                }
            })
    }, [])

    const dispatch = useDispatch();

    const toggleDrawer = () => {
        setOpenDrawer(!openDrawer);
    };

    function onItemClick(event: MouseEvent<HTMLDivElement>) {
        //console.log(event.target)
        getNoteDetails((event.target as HTMLElement).id)
            .then(res => {
                const resp = res as Response;
                if(!res || resp.status !== 200)
                    alert('Error!');
                console.log(resp);
                return resp.json();
            }).then(note => {
                setCurrentNote(note);
                setText(note?.text);
                setHead(note?.head);
        });
    }

    function handleLogout() {
        logout().then(r => {
            dispatch(Logout());
            dispatch(UpdateList([]));
            setText("");
            setHead("");
        });
    }

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
                    <Button className={classes.button}
                            variant="contained"
                            onClick={handleLogout}
                            color="primary">Logout</Button>
                </Toolbar>
            </AppBar>
            <div>
                <AdaptiveDrawer open={openDrawer}>
                    <List>
                        {notes.list && notes.list.map((note: Note, index: number) => (
                            <ListItem onClick={onItemClick} id={note.id as string} button key={note.id as string}>
                                {note.head}
                            </ListItem>
                        ))}
                    </List>
                </AdaptiveDrawer>
                <AuthDialog alert={alert} open={openDialog} close={onCloseDialog}/>
                <Snackbar open={alertMessage !== null} autoHideDuration={6000} >
                    <Alert severity="info" onClose={(event: SyntheticEvent<Element, Event>) => setAlertMessage(null)}>
                        {alertMessage}
                    </Alert>
                </Snackbar>
                <Typography className={clsx(classes.content, {[classes.contentShift]: openDrawer })}>
                    <Toolbar />
                    <TextField onChange={onHeadChange} className={classes.field} variant="outlined" value={head??"head"} label="head" fullWidth />
                    <TextField onChange={onTextChange} className={classes.field} variant="outlined" value={text??"text"} multiline label="text" rowsMax={25} rows={15} fullWidth />
                    <Toolbar>
                        <Button
                            variant="contained"
                            color="primary"
                            className={classes.button}
                            onClick={onSave}
                            disabled={(currentNote && currentNote?.head === head && currentNote?.text === text) as boolean}
                            startIcon={<SaveIcon />}>
                            Save
                        </Button>
                        <Button
                        variant="contained"
                        color="secondary"
                        onClick={deleteNote}
                        disabled={!currentNote}
                        className={classes.button}
                        startIcon={<DeleteIcon />}>
                        Delete
                    </Button>
                        <Button
                            onClick={onCancel}
                            className={classes.button}
                            variant="contained"
                            color="primary">
                            Cancel</Button>
                    </Toolbar>
                    Last update: {currentNote?.date}
                </Typography>
            </div>
        </div>
    );
}

export default App;
