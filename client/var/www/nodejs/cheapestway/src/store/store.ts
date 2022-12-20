import {configureStore} from "@reduxjs/toolkit";
import reducer from "./reducer";

export const store = configureStore({
    // @ts-ignore
    reducer: reducer,
});