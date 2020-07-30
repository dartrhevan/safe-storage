import React from 'react';
import {Hidden, Drawer, Toolbar, makeStyles, createStyles, Theme,} from '@material-ui/core';

const drawerWidth = 300;

interface IProps {
    open: boolean
}

const useStyles = makeStyles((theme: Theme) =>
    createStyles({
        root: {
            flexGrow: 1,
        },
        appBar: {
            zIndex: theme.zIndex.drawer + 1,
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
export default function ({children, open}: React.PropsWithChildren<IProps>) {
    const classes = useStyles();
    return (
        <>
            <Hidden smUp implementation="css">

                <Drawer
                    className={classes.drawer}
                    variant="persistent"
                    open={open}
                    classes={{
                        paper: classes.drawerPaper,
                    }}>

                    <Toolbar/>
                    {children}

                </Drawer>
            </Hidden>

            <Hidden xsDown implementation="css">
                <Drawer
                    className={classes.drawer}
                    variant="permanent"
                    classes={{
                        paper: classes.drawerPaper,
                    }}>

                    <Toolbar/>
                    {children}
                </Drawer>
            </Hidden>
        </>);
}