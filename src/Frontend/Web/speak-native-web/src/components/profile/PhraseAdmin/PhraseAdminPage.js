import React from "react";
import { phraseService } from "../../../services/phrase.service";
import { authService } from "../../../services/auth.service";
import { Formik, Field, Form, ErrorMessage } from 'formik';
import * as Yup from 'yup';

const DEFAULT=0;
const ADD=1;
const DELETE=2;

export class PhraseAdminPage extends React.Component {

    constructor(props) {
        super(props);

        this.state= {
            currentUser : authService.currentAuthValue.user,
            phrases : [],
            mode : DEFAULT
        }

        this.reloadPhrases = this.reloadPhrases.bind(this);
        this.setMode = this.setMode.bind(this);
        this.deletePhrases = this.deletePhrases.bind(this);
    }

    componentDidMount() {
        this.reloadPhrases();
    }

    reloadPhrases() {
        var { currentUser } = this.state;

        phraseService
            .getAll(currentUser.id)
            .then(
                data => {
                    this.setState({ 
                        mode : DEFAULT,
                        phrases : data 
                    })
            }
        )
    }

    setMode(newMode) {
        const { mode } = this.state;

        if(mode == newMode) {      
            this.setState({
                mode : DEFAULT
            });
            return;
        }

        this.setState({
            mode : newMode
        });
    }

    deletePhrases(userId, phraseIds) {
        phraseService
            .deleteAll(userId, phraseIds)
            .then(this.reloadPhrases);
    }

    render() {
        const { phrases } = this.state;
        const { mode } = this.state;
        const { currentUser } = this.state;

        return (
            <div> 
                Hello in admin 
                &nbsp;&nbsp;<button className="btn btn-sm btn-primary" onClick={()=>this.reloadPhrases()}> Reload </button>
                &nbsp;&nbsp;<button className="btn btn-sm btn-success" onClick={()=>this.setMode(ADD)}> Add new phrase </button>
                <hr/>
                {   
                    mode==ADD &&
                    <div>
                        <Formik
                            initialValues={{
                                targetLang: '',
                                referenceLang: ''
                            }}
                            validationSchema={Yup.object().shape({
                                targetLang: Yup.string().required('Value is required'),
                                referenceLang: Yup.string().required('Value is required')
                            })}
                            render={({ errors, status, touched, isSubmitting }) => (
                                <Form>
                                    <div className="form-group">
                                        <label htmlFor="targetLang">Target Language</label>
                                        <Field name="targetLang" type="text" className={'form-control' + (errors.targetLang && touched.targetLang ? ' is-invalid' : '')} />
                                        <ErrorMessage name="targetLang" component="div" className="invalid-feedback" />
                                    </div>
                                    <div className="form-group">
                                        <label htmlFor="referenceLang">Reference Language</label>
                                        <Field name="referenceLang" type="text" className={'form-control' + (errors.referenceLang && touched.referenceLang ? ' is-invalid' : '')} />
                                        <ErrorMessage name="referenceLang" component="div" className="invalid-feedback" />
                                    </div>
                                    <div className="form-group">
                                        <button type="submit" className="btn btn-success" disabled={isSubmitting}>Add Phrase</button>
                                        {isSubmitting &&
                                            <img src="data:image/gif;base64,R0lGODlhEAAQAPIAAP///wAAAMLCwkJCQgAAAGJiYoKCgpKSkiH/C05FVFNDQVBFMi4wAwEAAAAh/hpDcmVhdGVkIHdpdGggYWpheGxvYWQuaW5mbwAh+QQJCgAAACwAAAAAEAAQAAADMwi63P4wyklrE2MIOggZnAdOmGYJRbExwroUmcG2LmDEwnHQLVsYOd2mBzkYDAdKa+dIAAAh+QQJCgAAACwAAAAAEAAQAAADNAi63P5OjCEgG4QMu7DmikRxQlFUYDEZIGBMRVsaqHwctXXf7WEYB4Ag1xjihkMZsiUkKhIAIfkECQoAAAAsAAAAABAAEAAAAzYIujIjK8pByJDMlFYvBoVjHA70GU7xSUJhmKtwHPAKzLO9HMaoKwJZ7Rf8AYPDDzKpZBqfvwQAIfkECQoAAAAsAAAAABAAEAAAAzMIumIlK8oyhpHsnFZfhYumCYUhDAQxRIdhHBGqRoKw0R8DYlJd8z0fMDgsGo/IpHI5TAAAIfkECQoAAAAsAAAAABAAEAAAAzIIunInK0rnZBTwGPNMgQwmdsNgXGJUlIWEuR5oWUIpz8pAEAMe6TwfwyYsGo/IpFKSAAAh+QQJCgAAACwAAAAAEAAQAAADMwi6IMKQORfjdOe82p4wGccc4CEuQradylesojEMBgsUc2G7sDX3lQGBMLAJibufbSlKAAAh+QQJCgAAACwAAAAAEAAQAAADMgi63P7wCRHZnFVdmgHu2nFwlWCI3WGc3TSWhUFGxTAUkGCbtgENBMJAEJsxgMLWzpEAACH5BAkKAAAALAAAAAAQABAAAAMyCLrc/jDKSatlQtScKdceCAjDII7HcQ4EMTCpyrCuUBjCYRgHVtqlAiB1YhiCnlsRkAAAOwAAAAAAAAAAAA==" />
                                        }
                                    </div>
                                    {status &&
                                        <div className={'alert alert-danger'}>{status}</div>
                                    }
                                </Form>
                            )}
                            onSubmit={({ targetLang, referenceLang }, { setStatus, setSubmitting }) => {
                                setStatus();
                                phraseService
                                    .addOne({
                                        userId : currentUser.id,
                                        targetLang,
                                        referenceLang
                                    })
                                    .then( ()=> {
                                        setSubmitting(false);
                                        reloadPhrases();
                                        this.setMode(DEFAULT);
                                    })
                                    .catch(err => {
                                            setSubmitting(false);
                                            setStatus("Exception occured");
                                        });
                            }}
                            />
                    </div>
                }
                <ul className="list-group">{
                    phrases.map((phrase, index) =>
                        <li className="list-group-item p-1" key={phrase.id}> 
                            <button className="btn btn-sm btn-danger"
                                onClick={()=>this.deletePhrases(currentUser.id, [phrase.id])}>
                                    Delete
                            </button>
                            &nbsp;&nbsp; {index+1}.
                            &nbsp;&nbsp; {phrase.targetLang} - {phrase.referenceLang}
                        </li>
                    )
                } </ul>
            </div>
        );
    }
}