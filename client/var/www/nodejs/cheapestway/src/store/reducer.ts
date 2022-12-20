import * as actions from './actionTypes';

// @ts-ignore
export default function reducer(state = [], action) {
    switch (action.type) {
        case actions.ADD_STATE:
            return [...state, {
                data: action.payload.data
            }]
        default:
            return state;
    }
}