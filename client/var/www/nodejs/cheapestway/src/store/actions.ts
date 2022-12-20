import * as actions from './actionTypes';

// @ts-ignore
export const addState = state => ({
    type: actions.ADD_STATE,
    payload: state
});