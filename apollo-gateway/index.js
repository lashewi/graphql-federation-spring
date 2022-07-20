const { ApolloServer } = require('apollo-server');
const { ApolloGateway } = require('@apollo/gateway')

const gateway = new ApolloGateway({
    serviceList: [
        { name: 'user', url: 'http://localhost:8070/graphql' },
        { name: 'product', url: 'http://localhost:8071/graphql' },
        { name: 'review', url: 'http://localhost:8072/graphql' },
    ]
});

const server = new ApolloServer({
    gateway,
});


server.listen();

console.log(`🚀 Apollo Gateway now ready at http://localhost:4000/graphql`);