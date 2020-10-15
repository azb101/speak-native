USE SpeakNativeDB;
GO

--1. User table
CREATE TABLE [dbo].[AppUsers] (
    [Id] UNIQUEIDENTIFIER DEFAULT NEWID() PRIMARY KEY,
    [Email] NVARCHAR(50) NOT NULL UNIQUE,
    [Password] NVARCHAR(256) NOT NULL);
GO

--2. Create users seeds
-- passwords same for both users: temp
INSERT INTO [dbo].[AppUsers] ([Id], [Email], [Password])
VALUES 
    ('51802285-c3f6-4eb7-b293-238f0e58a49f','abuse@gmail.com', '$2a$10$XBHBcRB0cBK/wZG/9C2Hb.B8xlczT8BZ8syFBgGMNtdBuqSxdFWia'),
    ('75d94c2d-08c9-4773-b8e5-d768d878e95c','temp@gmail.com', '$2a$10$XBHBcRB0cBK/wZG/9C2Hb.B8xlczT8BZ8syFBgGMNtdBuqSxdFWia');
GO