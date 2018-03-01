package com.antmendoza.todoapp.rest;

interface Callback<P, R> {
    public R call(P param);
}