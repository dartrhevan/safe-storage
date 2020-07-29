import React from 'react';
import {AppBar, createStyles, Dialog, Tab, Tabs, Theme, withStyles} from "@material-ui/core";
import {makeStyles} from "@material-ui/core/styles";

import TabList from '@material-ui/lab/TabList';
import TabPanel from '@material-ui/lab/TabPanel';
import {TabContext} from "@material-ui/lab";
interface IProps {
    open: boolean
}


export default function ({open} : IProps) {
    const [value, setValue] = React.useState("0");

//    const classes = useStyles();
    const handleChange = (event: React.ChangeEvent<{}>, newValue: string) => {
        //alert(newValue)
        setValue(newValue);
    };

    return (
        <Dialog open={open}>
            <TabContext value={value}>
            <AppBar position="static">
            <TabList
                onChange={handleChange}
                indicatorColor="secondary"
                textColor="secondary"
                variant="standard">
                <Tab value="0" label="Login" />
                <Tab value="1" label="Register"/>
            </TabList>
            </AppBar>

            <TabPanel value="0">Item One</TabPanel>
            <TabPanel value="1">Item Two</TabPanel>

            </TabContext>
        </Dialog>
    )
}