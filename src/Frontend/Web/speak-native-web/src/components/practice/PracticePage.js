import React from "react";
import { authService } from "./../../services/auth.service";
import { practiceService } from "./../../services/practice.service";
import config from "config";
import { history } from "./../../helpers/history";
import { PhraseControl } from "./PhraseControl/PhraseControl";

export class PracticePage extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            currentUser: authService.currentAuthValue.user,
            phrases: [],
            notAnswered: []
        }

        this.getNextPhrases = this.getNextPhrases.bind(this);
        this.onNotAnsweredClicked = this.onNotAnsweredClicked.bind(this);
    }

    getNextPhrases() {
        const { notAnswered } = this.state;
        const userId = this.state.currentUser.id;

        if(!userId)
            history.push('/');
        
        this.setState({
            phrases : []
        });

        practiceService.getRandomPhrases(userId)
            .then(data => {
                this.setState( { 
                    phrases : data
                });
            });

        if(notAnswered && notAnswered.length > 0)
            practiceService.incrementFailingRates(userId, notAnswered);

        this.setState({
            notAnswered: []
        })
    };

    onNotAnsweredClicked(phraseId) {
        this.setState(prevState => ({
            notAnswered: [...prevState.notAnswered, phraseId]
        }))
    };

    render() {
        const { phrases } = this.state;

        return (
            <div>
                <button className="btn btn-primary btn-sm" onClick={this.getNextPhrases}>Get next phrases</button>
                <hr />
                {phrases &&
                    <ul className="list-group list-group-flush">
                        {
                            phrases.map( phrase =>
                                <li className="list-group-item border-0 p-0" key={phrase.id}> 
                                    <PhraseControl phrase={phrase} onNotAnsweredClick={this.onNotAnsweredClicked} />
                                </li>
                            )
                        }
                    </ul>
                }
            </div>
        );
    }
}