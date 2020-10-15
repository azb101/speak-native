
import React from "react";

const DEFAULT=0;
const ANSWERED=1;
const NOTANSWERED=2;

export class PhraseControl extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            status : DEFAULT
        }

        this.setStatus = this.setStatus.bind(this);
    }

    setStatus(status) {
        this.setState({
            status : status
        });

        if(status == NOTANSWERED)
            this.props.onNotAnsweredClick(this.props.phrase.id);
    }

    render(){
        let { status } = this.state;

        return (
            <div className={ "alert alert-" +
                ( status == DEFAULT 
                    ? "dark" 
                    : status == ANSWERED 
                        ? "success"
                        : "danger")
            }>
                { status==DEFAULT && <button className="btn btn-sm btn-success" onClick={()=> this.setStatus(ANSWERED)}>I know</button>} &nbsp; 
                { status==DEFAULT && <button className="btn btn-sm btn-danger" onClick={()=>this.setStatus(NOTANSWERED)}>Show translation</button> } &nbsp;
                {this.props.phrase.targetLang}

                { status==NOTANSWERED && <> - {this.props.phrase.referenceLang}</> }
                <span className="badge badge-primary badge-pill"> {this.props.phrase.failingRate} </span>
            </div>
        );
    }
}