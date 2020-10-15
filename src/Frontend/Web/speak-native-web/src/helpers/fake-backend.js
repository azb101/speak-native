

export function configureFakeBackend() {
    let users = [{ id: 1, email: 'test', password: 'test', firstName: 'Test', lastName: 'User' }];
    let phrases = [ { Id: 1, Eng: 'how can i make it up to you', Rus: 'Kak ya mogu zagladit vinu', UserId: 1 }];

    let realFetch = window.fetch;
    window.fetch = function (url, opts) {

        const isLoggedIn = opts.headers['Authorization'] === 'Bearer fake-jwt-token';
        return new Promise((resolve, reject) => {
            
            setTimeout(() => {
                // authenticate - public
                if (url.endsWith('/users/authenticate') && opts.method === 'POST') {
                    const params = JSON.parse(opts.body);
                    
                    const user = users.find(x => x.email === params.email && x.password === params.password);
                    if (!user) 
                        return error('Email or password is incorrect');

                    return ok({
                        id: user.id,
                        email: user.email,
                        firstName: user.firstName,
                        lastName: user.lastName,
                        token: 'fake-jwt-token'
                    });
                }

                // get users - secure
                if (url.endsWith('/users') && opts.method === 'GET') {
                    if (!isLoggedIn) 
                        return unauthorised();

                    return ok(users);
                }

                // get random phrases
                if (url.endsWith('/phrases/1/random/10') && opts.method === 'POST') {
                    if (!isLoggedIn) 
                        return unauthorised();

                    return ok(phrases.filter(s=> s.UserId === 1));
                }

                // pass through any requests not handled above
                realFetch(url, opts).then(response => resolve(response));

                // private helper functions

                function ok(body) {
                    resolve({ ok: true, text: () => Promise.resolve(JSON.stringify(body)) })
                }

                function unauthorised() {
                    resolve({ status: 401, text: () => Promise.resolve(JSON.stringify({ message: 'Unauthorised' })) })
                }

                function error(message) {
                    resolve({ status: 400, text: () => Promise.resolve(JSON.stringify({ message })) })
                }
            }, 500);
        });
    }
}