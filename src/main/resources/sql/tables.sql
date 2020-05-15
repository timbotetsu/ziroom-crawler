CREATE TABLE ROOM
(
    ID             BIGINT PRIMARY KEY,
    NAME           TEXT                      NOT NULL,
    PRICE          INT         DEFAULT 0,
    PHOTO          TEXT,
    DESCRIPTION    TEXT,
    APARTMENT_TYPE INT,
    PRICE_UNIT     TEXT,
    PHOTO_ALT      TEXT,
    SALE_CLASS     TEXT,
    DETAIL_URL     TEXT,
    SALE_STATUS    INT,
    CAN_SIGN_TIME  BIGINT,
    CAN_SIGN_LONG  BIGINT,
    RESBLOCK_ID    TEXT,
    RESBLOCK_NAME  TEXT,
    AGENT_END_DATE BIGINT,
    CREATE_TIME    TIMESTAMPTZ DEFAULT NOW() NOT NULL
);

