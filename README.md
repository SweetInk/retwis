# Retwis
Retwis is the Redis Hello World. A minimal Twitter-style social network clone
written using Redis and JAVA. The source code is designed to be very simple and
at the same time to show different Redis data structures.

You can find a tutorial explaining step by step how the example was created
in the [Twitter clone tutorial of the Redis documentation](http://redis.io/topics/twitter-clone).

This code was rewritten use spring boot 

## origin repository ##
[https://github.com/antirez/retwis](https://github.com/antirez/retwis)




The following is a description of the data layout.

Users
---

User IDs are generated sequencially via increments:

    INCR next_user_id => 1000

(We'll use the example user ID 1000 in the next examples)

Users are stored into Redis hashes, the key name of the hash representing a
given user is `user:1000`. Every hash has the following fields:

    username (the username of the user)
    password (password of course)
    auth 9458sd893448dfd

There are additional keys for following, followers, and posts:

    following:1000 (sorted set of user ids)
    followers:1000 (sorted set of user ids)
    posts:1000 (list of posts ids)

In order to reverse lookup the user ID from the authentication toke or
username we have the following two additional keys:

    The key `auths` is an hash mapping auth tokens to user IDs.
    Example of field and value: 9458sd893448dfd => 1000

    The key `users` is an hash mapping usernames to user IDs.
    Example of field and value: antirez => 1000

Additionally we take a sorted set with usernames indexed by registration
time (the score is the unix time the user joined), so that we can populate
our "latest users" view. The key is called `users_by_time`.

Posts
---

Posts also have sequencial IDs, generated by incrementing the following key:

    INCR next_post_id => 134

Every post is stored into an hash named `post:134` with the following
fields:

    user_id
    time
    body

Timeline
---

Just a list of post IDs in the `timeline` key.
