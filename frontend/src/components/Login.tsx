import React, { ChangeEvent } from 'react';
import {login} from '../api';
import {
    Button,
    Container,
    TextField,
} from "@material-ui/core";
import {makeStyles} from "@material-ui/core/styles";
import {strict} from "assert";
import { useStore, useDispatch } from 'react-redux';
import {setUsername as setStateUsername} from '../store/Auth/actions';

const useStyles = makeStyles({
    root: {
    },
    header: {
        margin: 0
    }
});

interface IProps {
    close: ()=>void
}

export default function ({ close }: IProps) {
    const classes = useStyles();
    const [username, setUsername] = React.useState<string>("");
    const [password, setPassword] = React.useState<string>("");
    const store = useStore();
    const dispatch = useDispatch();
    const d = () =>{};
    function onLoginClick() {
        login(username, password)
            .then((r: void | Response) => {
                const resp = r as Response;
                console.log(resp)
                if(!resp || resp.status !== 200)
                    alert('Error!');
                else {
                    dispatch(setStateUsername(username));
                    console.log(store.getState());
                    close();
                }
            })
    }

    function handleChangePassword(event: ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) {
        setPassword(event.target.value);
    }

    function handleChangeUsername(event: ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) {
        setUsername(event.target.value);
    }

    return (<Container>
        <h4 className={classes.header}>Authorisation</h4>
        <TextField onChange={handleChangeUsername} label="Login" required />
        <br/>
        <TextField type="password" onChange={handleChangePassword} label="Password" required />
        <br/>
        <br/>
        <Button onClick={onLoginClick} variant="contained" color="primary">Confirm</Button>
    </Container>)
}