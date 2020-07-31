import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import { Provider } from 'react-redux';
import store from './store/store';
import { ThemeProvider, createMuiTheme } from '@material-ui/core';
import { red, blue } from '@material-ui/core/colors';

const theme = createMuiTheme({
    palette: {
        primary: {
            main: blue[400],
        },
        secondary: {
            main: red[800],
        },
    },
});

ReactDOM.render(
  <React.StrictMode>
      <Provider store={store}>
          <ThemeProvider theme={theme}>
            <App />
          </ThemeProvider>
      </Provider>
  </React.StrictMode>,
  document.getElementById('root')
);

