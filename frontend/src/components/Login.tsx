import React from 'react';
import {
    Button,
    Container,
    TextField,
} from "@material-ui/core";
import {makeStyles} from "@material-ui/core/styles";

const useStyles = makeStyles({
    root: {
    },
    header: {
        margin: 0
    }
});

export default function () {
    const classes = useStyles();
    const loginRef = React.createRef<HTMLInputElement>();
    const passwordRef = React.createRef<HTMLInputElement>();
    return (<Container>
        <h4 className={classes.header}>Authorisation</h4>
        <TextField ref={loginRef} label="Login" required />
        <br/>
        <TextField ref={passwordRef} label="Password" required />
        <br/>
        <br/>
        <Button variant="contained" color="primary">Confirm</Button>
    </Container>)
}