package com.asmirnov.quilzistServer.domain;

public class Views {
    public interface Id{};

    public interface IdName extends Id{};

    public interface Info extends IdName{};

    public interface FullData extends Info{};
}
