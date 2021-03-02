const path = require('path');
const { CleanWebpackPlugin } = require('clean-webpack-plugin');

module.exports = {
    entry: {
        createstudyset: './src/createstudyset.js',
        homepage: './src/homepage.js',
        main: './src/main.js',
        searchcontents: './src/searchcontent.js',
        studyset: './src/studyset.js',
        usercenter: './src/usercenter.js'
    },
    output: {
        filename: '[name].bundle.js',
        path: path.resolve(__dirname, 'dist'),
    },
    module: {
        rules: [{
                test: /\.css$/i,
                use: ['style-loader', 'css-loader'],
            },
            {
                test: /\.(png|svg|jpg|jpeg|gif|webp)$/i,
                type: 'asset/resource',
                generator: {
                    filename: '../dist/picture/[name][ext]'
                }
            },
            {
                test: /\.(woff|woff2|eot|ttf|otf)$/i,
                type: 'asset/resource',
            }
        ],
    },
    plugins:[
        new CleanWebpackPlugin({
            cleanOnceBeforeBuildPatterns: ['!*.html']
        })
    ]
};