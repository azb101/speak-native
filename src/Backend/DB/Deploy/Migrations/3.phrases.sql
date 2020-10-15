USE SpeakNativeDB;
GO

--1. phrases table
CREATE TABLE [dbo].[Phrases] (
    [Id] UNIQUEIDENTIFIER DEFAULT NEWID() PRIMARY KEY,
    [UserId] UNIQUEIDENTIFIER NOT NULL,
    [ReferenceLang] NVARCHAR(300) NOT NULL, 
    [TargetLang] NVARCHAR(300) NOT NULL,
    [CreatedAt] DATETIME NOT NULL DEFAULT GETUTCDATE(),
    [FailingRate] INT NOT NULL DEFAULT 1,
    CONSTRAINT fk_users_id FOREIGN KEY ([UserId]) REFERENCES [dbo].[AppUsers](Id) );
GO

--2. phrases table seed
INSERT INTO [dbo].[Phrases]([UserId], [ReferenceLang], [TargetLang])
VALUES 
    ('51802285-c3f6-4eb7-b293-238f0e58a49f', "How can I make it up to you", "Как я могу загладить свою вину"),
    ('51802285-c3f6-4eb7-b293-238f0e58a49f', "Get it together bro", "Соберись тряпка"),
    ('75d94c2d-08c9-4773-b8e5-d768d878e95c', "How should I put it correctly", "Как бы покоректнее выразиться");
GO