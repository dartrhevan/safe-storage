import React, { ChangeEvent } from 'react';
import {
    Button,
    Container,
    TextField
} from "@material-ui/core";
import {makeStyles} from "@material-ui/core/styles";
import { useDispatch, useStore } from 'react-redux';
import { registration } from '../api';



const useStyles = makeStyles({
    root: {
    },
    header: {
        margin: 0
    }
});

export default function () {

    const classes = useStyles();

    const store = useStore();
    const dispatch = useDispatch();
    const [username, setUsername] = React.useState<string>("");
    const [password, setPassword] = React.useState<string>("");
    const [passwordConf, setPasswordConf] = React.useState<string>("");


    function handleChangePasswordConf(event: ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) {
        setPasswordConf(event.target.value);
    }

    function handleChangePassword(event: ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) {
        setPassword(event.target.value);
    }

    function handleChangeUsername(event: ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) {
        setUsername(event.target.value);
    }
    function onRegistrationClick() {
        if(password !== passwordConf)
            alert("password !== passwordConf")
        else
        registration(username, password)
            .then((r: void | Response) => {
                const resp = r as Response;
                console.log(resp)
                if(!resp || resp.status !== 200)
                    alert('Error!');
                else
                    return resp.text();
            })
            .then(t => alert(t === "" ? "Succes" : t))
    }

    return (<Container>
        <h4 className={classes.header}>Registration</h4>
        <TextField onChange={handleChangeUsername} label="Login" required />
        <br/>
        <TextField type="password" onChange={handleChangePassword} label="Password" required />
        <br/>
        <TextField type="password" onChange={handleChangePasswordConf} label="Confirm password" required />
        <br/>
        <br/>
        <Button onClick={onRegistrationClick} variant="contained" color="primary">Register</Button>
    </Container>);
}