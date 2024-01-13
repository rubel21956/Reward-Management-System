// @ts-check
// Protractor configuration file, see link for more information
// https://github.com/angular/protractor/blob/master/lib/config.ts

const { SpecReporter } = require('jasmine-spec-reporter');
var HtmlReporter = require('protractor-beautiful-reporter');

/**
 * @type { import("protractor").Config }
 */
exports.config = {
    allScriptsTimeout: 6000000,
    // specs: [
    //     './src/**/*.e2e-spec.ts'
        
    // ],

    // SELENIUM_PROMISE_MANAGER: false,
    capabilities: {
        browserName: 'chrome',
        chromeOptions: {
            prefs: { 'profile.default_content_setting_values.media_stream_camera': 1 },
            //args: ['--headless', '--window-size=1920x1080', '--log-level=3'],
            // args: ['--window-size=1920x1080', '--log-level=3']

        }
    },

    suites: {
        approver_my_locked_list: './src/approver-my-locked-list/*.e2e-spec.ts',
        approver_remarks: './src/approver-remarks/*.e2e-spec.ts',
        edit_remarked_user_from_new_list: './src/edit-remarked-user-from-new-list/*.e2e-spec.ts',
        maker_my_locked_list: './src/maker-my-locked-list/*.e2e-spec.ts',
        user_creation_approver_all_list: './src/user-creation-approver-all-list/*.e2e-spec.ts',
        user_creation_maker_all_list: './src/user-creation-maker-all-list/*.e2e-spec.ts',
        user_management_add_new: './src/user-management/add-new/*.e2e-spec.ts',
        user_management_approve: './src/user-management/approve/*.e2e-spec.ts',
        user_management_remark_user: './src/user-management/remark-user/*.e2e-spec.ts',
        userInfo_approver: './src/logged-in-user-info-approver/*.e2e-spec.ts'

    },
    //exclude: ['./src/landing-page-language-selection/*.e2e-spec.ts', './src/language-selection/central-approver-language_selection/*.e2e-spec.ts', './src/language-selection/identity-verifier-language-selection/*.e2e-spec.ts'],


    directConnect: true,
    baseUrl: 'http://localhost:4200',
    //baseUrl: 'https://',
    //baseUrl: 'https://dev-test.celloscope.net/admin',
    //baseUrl: 'https://dev2-test.celloscope.net/admin',

    jasmineNodeOpts: {
        showColors: true,
        defaultTimeoutInterval: 600000,
        print: function () { }
    },

    onPrepare() {
        require('ts-node').register({
            project: require('path').join(__dirname, './tsconfig.json')
        });
        jasmine.getEnv().addReporter(new SpecReporter({ spec: { displayStacktrace: true } }));
        jasmine.getEnv().addReporter(new HtmlReporter({
            baseDirectory: 'admin-test-report',
            preserveDirectory: false,
            takeScreenShotsOnlyForFailedSpecs: false
        }).getJasmine2Reporter());
    }
};
